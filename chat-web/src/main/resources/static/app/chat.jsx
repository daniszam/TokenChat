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
                {id: 1, name: "first", active: false, messages: []},
                {id: 2, name: "second", active: false, messages: []},
            ],
            dialog: undefined,
            subscription: undefined,
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
        this.setState({dialog: dialog});
        this.setState({messages: dialog.messages});
        let subscription = this.state.stompClient.subscribe(env.APP.SUBSCRIBE_URL + "/" + dialog.id,  (response) => {
            let message = JSON.parse(response['body']);
            this.state.dialog.messages.push({
                from: message['from'],
                text: message['text'],
                isMyMessage: message['from'] === this.state.username,
            });
            this.setState({messages : this.state.dialog.messages})
        });

        this.setState({subscription: subscription});
    }

    stompSendMessage() {
        let message = {
            'text': this.state.message,
            'from': this.state.username,
        };
        this.state.stompClient.send(env.APP.STOMP_MESSAGE + "/" + this.state.dialog.id, {}, JSON.stringify(message))
    }

    dialogClickEvent(dialog) {
        if (this.state.subscription) {
            this.state.subscription.unsubscribe();
        }
        if (!this.state.subscription || this.state.stompClient && this.state.dialog && dialog.id !== this.state.dialog.id) {
            this.stompSubscribe(dialog);
            dialog.active = true;
            this.setState({dialog: dialog});
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
                                                messages={dialog.messages} active={dialog.active}/>
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