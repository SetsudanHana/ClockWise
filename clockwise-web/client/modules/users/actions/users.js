export default {
	login({Meteor, LocalState, FlowRouter}, login, password) {
    if (!login) {
      return LocalState.set('LOGIN_ERROR', 'Login jest wymagany.');
    }

    if (!password) {
      return LocalState.set('LOGIN_ERROR', 'Hasło jest wymagane.');
    }

    const {responseToken} = Meteor.subscribe('user.login', login, password);
    if(responseToken) {
      FlowRouter.go('/dashboard');
    } else {
      return LocalState.set('LOGIN_ERROR', 'Problem przy logowaniu.');
    }

    LocalState.set('LOGIN_ERROR', null);
  },

  clearErrors({LocalState}) {
    return LocalState.set('SAVING_ERROR', null);
  }
};