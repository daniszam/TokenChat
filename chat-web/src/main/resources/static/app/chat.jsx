import React, {Component} from 'react';
import env from '../enviroment';
import Message from "./message";

class Chat extends Component {

    constructor(props) {
        super(props);
        this.state = {
            message: '',
            username: localStorage.getItem('username'),
            ws: undefined,
            messages : []
        };
        this.updateMessage = this.updateMessage.bind(this);
        this.sendMessage = this.sendMessage.bind(this);
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

    componentDidMount() {
        document.cookie= 'Authorization='+localStorage.getItem('accessToken');
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
        };
        this.state.ws.send(JSON.stringify(message));
    }

    render() {
        return (
            <div className="container align-middle">
                {
                    this.state.messages.map((message) => (
                        <Message isMyMessage={message.isMyMessage} text={message.text} from={message.from}/>
                    ))
                }
                // input text
                <div className="input-group">
                    <div className="input-group-prepend">
                <span className="input-group-text">
                    Text
                </span>
                    </div>
                    <textarea className="form-control" aria-label="With textarea" value={this.state.message}
                              onChange={this.updateMessage}>
                    </textarea>
                    <button className="btn btn-primary" onClick={this.sendMessage}>Send</button>
                </div>
                <button type="button" className="btn btn-primary" onClick={event => this.exit(this.props)}>Exit</button>
            </div>
        );
    }
}

export default Chat;