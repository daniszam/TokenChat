import React, {Component} from 'react';
import PropTypes from 'prop-types';

class Dialog extends Component {

    constructor(props) {
        super(props);
        this.state = {
            backgroundColor: '#ededed'
        }

        this.changeBackground = this.changeBackground.bind(this);
        this.onBlur = this.onBlur.bind(this);
    }

    changeBackground(){
        this.setState({backgroundColor: "blue"})
    }

    onBlur() {
        this.setState({backgroundColor: '#ededed'})
    }

    render() {
        return (
            <div className="card w-50" style={{backgroundColor: this.state.backgroundColor}}
                 onFocus={this.changeBackground} onBlur={this.onBlur}>
                <div className="card-body" onFocus={this.changeBackground}>
                    <h5 className="card-title" onFocus={this.changeBackground}>{this.props.name}</h5>
                </div>
            </div>
        )
    }
}

export default Dialog;

Dialog.propTypes = {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    messages: PropTypes.array.isRequired,
};
