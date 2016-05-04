export default {
	login({Meteor, LocalState, FlowRouter}, login, password) {
    if (!login) {
      return LocalState.set('LOGIN_ERROR', 'Login jest wymagany.');
    }

    if (!password) {
      return LocalState.set('LOGIN_ERROR', 'Hasło jest wymagane.');
    }

    console.log("Getting ready for subscription");

    var responseToken = Meteor.subscribe('user.login', login, password);
    if(responseToken) {
      FlowRouter.go('/dashboard');
    } else {
      return LocalState.set('LOGIN_ERROR', 'Problem przy logowaniu.');
    }

    LocalState.set('LOGIN_ERROR', null);
  },
  
  register({Meteor, FlowRouter, LocalState}, email, password, confirmPassword) {
      if (!email) {
        return LocalState.set('CREATE_USER_ERROR', 'Email is required.');
      }

      if (!password || !confirmPassword) {
        return LocalState.set('CREATE_USER_ERROR', 'Password is required.');
      }
      
      if(password !== confirmPassword) {
        return LocalState.get('CREATE_USER_ERROR', "Passwords don't match.");
      }
      
      LocalState.set('CREATE_USER_ERROR', null);

      var responseToken = Meteor.subscribe('user.register', login, password);
      if(responseToken) {
        FlowRouter.go('/dashboard');
      } else {
        return LocalState.set('CREATE_USER_ERROR', 'Problem przy rejestracji.');
      }
  },

  clearErrors({LocalState}) {
    return LocalState.set('SAVING_ERROR', null);
  }
};