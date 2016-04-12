export default {
	login({Meteor, LocalState, FlowRouter}, login, password) {
    if (!login) {
      return LocalState.set('LOGIN_ERROR', 'Login jest wymagany.');
    }

    if (!password) {
      return LocalState.set('LOGIN_ERROR', 'Hasło jest wymagane.');
    }

    LocalState.set('LOGIN_ERROR', null);

    // Meteor.loginWithPassword(login, password);
    // FlowRouter.go('/');
  },

  clearErrors({LocalState}) {
    return LocalState.set('SAVING_ERROR', null);
  }
};