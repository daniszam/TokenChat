import React, {Component} from 'react';
import PropTypes from 'prop-types';

class Dialog extends Component {

    render() {
        return (
            <div className="card w-50">
                <div className="card-body">
                    <h5 className="card-title">{this.props.name}</h5>
                </div>
            </div>
        )
    }
}

export default Dialog;

Dialog.propTypes = {
    // id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
};
