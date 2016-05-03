import Register from '../components/Register.jsx';
import {useDeps, composeWithTracker, composeAll} from 'mantra-core';

export const composer = ({context, clearErrors}, onData) => {
    const {LocalState} = context();
    const error = LocalState.get('CREATE_USER_ERROR');
    onData(null, {error});

    return clearErrors;
};

export const depsMapper = (context, actions) => ({
    registerUser: actions.users.register,
    clearErrors: actions.users.clearErrors,
    context: () => context
});

export default composeAll(
    composeWithTracker(composer),
    useDeps(depsMapper)
)(Register);