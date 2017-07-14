import 'app/styles/login.styles'
import templateUrl from 'app/html/login.template'

const controller = class FtLoginController {
  constructor ($log, $state, $location, $http, ftGameSettings, $window, $rootScope, appService, localStorageService) {
    'ngInject'
    this.localStorageService = localStorageService
    this.service = appService
    this.settings = ftGameSettings
    this.$location = $location
    this.$window = $window
    this.$state = $state
    this.$http = $http
    $rootScope.$on(this.$location.$routeChangeStart, this.checkAuth())
    $log.log('ft-login is a go')
  }
  checkAuth () {
    let isauthenticated = this.localStorageService.get('isAuthenticated')
    if(isauthenticated) {
      this.$state.transitionTo('home')
    }
  }
  saveState (key, val) {
    this.localStorageService.set(key, val)
  }
  loginSubmit () {
    let user = this.username
    let pass = this.password
    this.$http({
      method: 'POST',
      url: 'http://localhost:8080/user/users/@' + this.username,
      data: {credentials: { password: this.password, username: this.username }}
    }).then(this.successCallback = (response) => {
      if (response != 404)
      this.saveState('username', user)
      this.saveState('password', pass)
      this.saveState('isAuthenticated', true)
      this.$state.transitionTo('home')
    }, this.errorCallback = (response) => {
      this.error = 'Username or password are incorrect, please try again.'
    })
  }
  registerSubmit () {
      this.$http({
        method: 'POST',
        url: 'http://localhost:8080/user/users',
         params: { firstName: this.firstname, lastName: this.lastname, phone: this.phone },
        data:
        {
          "credentials": {
            "password": this.regpassword,
            "username": this.regusername
            },
            "profile": {
              "email": this.email
            }
          }
        })
     .then((response) => {
      // alert(JSON.stringify(response.data) + '3')
       if (response.status === 201) {
         this.saveState('username', this.regusername)
         this.saveState('password', this.regpassword)
         this.saveState('isAuthenticated', true)
         this.$state.transitionTo('home')
       }
     })
  }
  checkUser () {
    this.$http({
      method: 'GET',
      url: 'http://localhost:8080/validate/username/available/@' + this.regusername + ''
    }).then(this.successCallback = (response) => {
      return true
    }, this.errorCallback = (response) => {
      this.error = 'Username or password are incorrect, please try again.'
    })
  }
}
export const ftLogin = {
  controller,
  templateUrl,
  controllerAs: 'login'
}
