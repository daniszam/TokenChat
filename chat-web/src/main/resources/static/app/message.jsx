import React, {Component} from 'react';
import PropTypes from 'prop-types'


class Message extends Component {

    render() {
        return (
            <div >
                <div className={'card col-md-4 ' + (this.props.isMyMessage ? 'offset-md-8':'offset-md-1') } style={{width: 18 + 'rem'}}>
                    <div className="card-body">
                        <h6 className="card-subtitle mb-2 text-muted">{this.props.from}</h6>
                        <p className="card-text">{this.props.text}</p>
                    </div>
                </div>
            </div>
        )
    }
}

export default Message;

Message.propTypes = {
    isMyMessage: PropTypes.bool.isRequired,
    from: PropTypes.string.isRequired,
    text: PropTypes.string.isRequired,
};
