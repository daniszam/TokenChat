import React, {Component} from 'react';
import PropTypes from 'prop-types';

class Dialog extends Component {

    constructor(props) {
        super(props);
        this.state = {
            backgroundColor: '#ededed'
        };

        this.changeBackground = this.changeBackground.bind(this);
        this.onBlur = this.onBlur.bind(this);
    }

    changeBackground() {
        this.setState({backgroundColor: "blue"})
    }

    onBlur() {
        this.setState({backgroundColor: '#ededed'})
    }

    render() {
        return (
            <div className="card" style={{backgroundColor:this.state.active ? "blue" : this.state.backgroundColor}}
                 onMouseEnter={this.changeBackground} onMouseLeave={this.onBlur}>
                <div className="card-body">
                    <h5 className="card-title">{this.props.name}</h5>
                </div>
            </div>
        )
    }
}

export default Dialog;

Dialog.propTypes = {
    active: PropTypes.bool.isRequired,
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
    messages: PropTypes.array.isRequired,
};
