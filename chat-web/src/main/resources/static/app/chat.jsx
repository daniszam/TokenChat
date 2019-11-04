import React, {Component} from 'react';
import env from '../enviroment';

class Chat extends Component {

    exit(props) {
        localStorage.removeItem('accessToken');
        props.history.push(env.APP.LOGIN);
    }

    render() {
        return (
            <div>
                <div className="card" style={{width: 18 + 'rem'}}>
                    <div className="card-body">
                        <h5 className="card-title">Card title</h5>
                        <h6 className="card-subtitle mb-2 text-muted">Card subtitle</h6>
                        <p className="card-text">Some quick example text to build on the card title and make up the bulk
                            of the card's content.</p>
                        <a href="#" className="card-link">Card link</a>
                        <a href="#" className="card-link">Another link</a>
                    </div>
                </div>

                <div style={{position: 'absolute', top: 0, right: 0}}>

                <div className="card" style={{width: 18 + 'rem'}}>
                    <div className="card-body">
                        <h5 className="card-title">Card title</h5>
                        <h6 className="card-subtitle mb-2 text-muted">Card subtitle</h6>
                        <p className="card-text">Some quick example text to build on the card title and make up the bulk
                            of the card's content.</p>
                        <a href="#" className="card-link">Card link</a>
                        <a href="#" className="card-link">Another link</a>
                    </div>
                </div>
                </div>

                // input text
                <div className="input-group">
                    <div className="input-group-prepend">
                <span className="input-group-text">
                    Withtextarea
                </span>
                    </div>
                    <textarea className="form-control" aria-label="With textarea">
                    </textarea>
                </div>
                <button type="button" className="btn btn-primary" onClick={event => this.exit(this.props)}>Exit</button>
            </div>
        );
    }
}


export default Chat;