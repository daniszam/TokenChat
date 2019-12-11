import React, {Component} from 'react';
import env from '../enviroment';
import Message from "./message";
import Dialog from "./dialog";
import SockJS from "sockjs-client"
import {Stomp} from "@stomp/stompjs"


class Chat extends Component {

    constructor(props) {
        super(props);
        this.state = {
            message: '',
            username: localStorage.getItem('username'),
            ws: undefined,
            messages: [],
            dialogs: [
                {id: 1, name: "first", messages: []},
                {id: 2, name: "second", messages: []},
            ],
            dialog: undefined,
            websocketOn: true,
            pageId: '',
            stompClient: undefined
        };
        this.updateMessage = this.updateMessage.bind(this);
        this.sendMessage = this.sendMessage.bind(this);
        this.changeChatType = this.changeChatType.bind(this);
        this.connectWebSocket = this.connectWebSocket.bind(this);
        this.stompSendMessage = this.stompSendMessage.bind(this);
        this.stompSubscribe = this.stompSubscribe.bind(this);
        this.connectStomp = this.connectStomp.bind(this);
        this.dialogClickEvent = this.dialogClickEvent.bind(this);
    }

    exit(props) {
        localStorage.removeItem('accessToken');
        props.history.push(env.APP.LOGIN);
    }

    updateMessage(event) {
        this.setState({
            message: event.target.value
        })
    }

    changeChatType() {
        let websocketOn = !this.state.websocketOn;
        this.setState({
            websocketOn: websocketOn
        });

        if (websocketOn) {
            this.connectWebSocket();
        } else {
            this.state.ws.close();
            this.connectLongPooling();
        }
    }

    connectLongPooling() {
        $.ajax({
            url: env.LONG_POOLING_CONNECT,
            method: 'GET',
            headers: {
                'Authorization': localStorage.getItem('accessToken')
            },
            contentType: env.APPLICATION_JSON,
            dataType: 'text',
            success: (response) => {
                this.setState({
                    pageId: response
                });
                this.receiveMessage(response);
            },
            error: (response) => {

            }
        })
    }

    componentDidMount() {
        this.connectStomp();
        // this.connectWebSocket();
    }

    connectStomp() {
        document.cookie = 'Authorization=' + localStorage.getItem('accessToken');
        let socket = new SockJS(env.SERVER_URL + env.APP.STOMP_URL);
        let stompClient = Stomp.over(socket);
        this.setState({stompClient: stompClient});
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
        });
    }

    stompSubscribe(dialog) {
        this.state.stompClient.subscribe('/topic/messages/' + dialog.id, function (message) {
            console.log('Subscribe to topic/chat');
            // this.state.dialog.messages.concat([{
            //     from: message['from'],
            //     text: message['text'],
            //     isMyMessage: message['from'] === this.state.username,
            // }]);
            this.state.messages.clear();
            this.setState({dialog: dialog});
            this.setState({messages: this.state.dialog.messages});
        });
    }

    stompSendMessage() {
        let message = {
            'text': this.state.message,
            'from': this.state.username,
        };
        this.state.stompClient.send("/app/api/chat/stomp/" + this.state.dialog.id, {}, JSON.stringify(message))
    }

    dialogClickEvent(dialog) {
        if (this.state.stompClient) {
            this.state.stompClient.unsubscribe('/topic/messages/' + dialog.id);
            this.stompSubscribe(dialog);
            dialog.onBlur();
            this.setState({dialog: dialog});
            this.state.dialog.changeBackground();
        }
    }

    connectWebSocket() {
        document.cookie = 'Authorization=' + localStorage.getItem('accessToken');
        let ws = new WebSocket(env.WEBSOCKET_URL);
        this.setState({ws: ws});
        ws.onopen = () => {
            console.log('websocket connected');
        };

        ws.onerror = () => {
            localStorage.removeItem('accessToken');
            this.props.history.push(env.APP.LOGIN);
        };

        ws.onclose = () => {
            console.log('websocket closed');
        };

        ws.onmessage = (response) => {
            console.log('you have new message ' + response);
            let message = JSON.parse(response['data']);
            let messages = this.state.messages.concat([{
                from: message['from'],
                text: message['text'],
                isMyMessage: message['from'] === this.state.username,
            }]);
            this.setState({
                messages: messages
            });
        }
    }

    sendMessage() {
        let message = {
            'text': this.state.message,
            'from': this.state.username,
            'pageId': this.state.pageId,
        };

        if (this.state.websocketOn) {
            this.state.ws.send(JSON.stringify(message));
        } else {
            $.ajax({
                url: env.LONG_POOLING,
                method: 'POST',
                data: JSON.stringify(message),
                headers: {
                    'Authorization': localStorage.getItem('accessToken')
                },
                contentType: env.APPLICATION_JSON,
                dataType: 'json',
            })
        }
    }

    receiveMessage() {
        $.ajax({
            url: env.LONG_POOLING_MESSAGES + this.state.pageId,
            method: "GET",
            dataType: "json",
            contentType: "application/json",
            headers: {
                'Authorization': localStorage.getItem('accessToken')
            },
            success: (response) => {
                let messages = [];
                response.forEach((item) => {
                    messages.push({
                        from: item.pageId,
                        text: item.text,
                        isMyMessage: item.pageId === this.state.pageId,
                    })
                });
                this.setState({
                    messages: this.state.messages.concat(messages)
                });
                this.receiveMessage(this.state.pageId);
            }
        })
    }

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <div className="dialogs">
                            {
                                this.state.dialogs.map((dialog) => (
                                    <div className="dialog" onClick={()=>this.dialogClickEvent(dialog)} onFocus={()=>this.dialogClickEvent(dialog)}>
                                        <Dialog key={dialog.id} name={dialog.name} id={dialog.id}
                                                messages={dialog.messages}/>
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                    <div className="col-8">
                        <div className="container align-middle">
                            {/*<div className="checkbox">*/}
                            {/*    <label>*/}
                            {/*        <input type="checkbox" onChange={this.changeChatType}*/}
                            {/*               defaultChecked={this.state.websocketOn}/>*/}
                            {/*        WebSocket On*/}
                            {/*    </label>*/}
                            {/*</div>*/}
                            <div className="container">
                                {
                                    this.state.messages.map((message) => (
                                        <Message isMyMessage={message.isMyMessage} text={message.text}
                                                 from={message.from}/>
                                    ))
                                }
                            </div>
                            <div className="input-group">
                                <div className="input-group-prepend">
                                    <span className="input-group-text">
                                        Text
                                    </span>
                                </div>
                                <textarea className="form-control" aria-label="With textarea" value={this.state.message}
                                          onChange={this.updateMessage}>
                                 </textarea>
                                <button className="btn btn-primary" onClick={this.stompSendMessage}>Send</button>
                            </div>
                            <button type="button" className="btn btn-primary"
                                    onClick={event => this.exit(this.props)}>Exit
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Chat;