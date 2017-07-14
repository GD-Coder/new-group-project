import 'app/styles/usersettings.styles'
import templateUrl from 'app/html/usersettings.template'

const controller = class FtUserSettingsController {
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
    $log.log('ft-usersettings is a go')
  }
  checkAuth () {
    let isauthenticated = this.localStorageService.get('isAuthenticated')
    if(!isauthenticated) {
      this.$state.transitionTo('login')
    }
  }
updateUser(){
  this.$http({
    method: 'PATCH',
    url: 'http://localhost:8080/user/users/@' + this.username + '',
    params: { firstName: this.firstname, lastName: this.lastname, phone: this.phone, username:this.username, email:this.email },
    data:
    {
    "credentials": {
      "password": this.password,
      "username": this.username
    }
  }
  }).then((response) => {
      alert(JSON.stringify(response.data) + '3')
      if (response.status === 302) {
        alert('User Created!' + ' ' + '201')
      }
    }, (error) => {
      console.log('someshit went wrong yo', error.data)
    })
}

deleteUser () {
  console.log(`${this.password} and ${this.username}`)
  let test = {
    credentials: {
      username: this.username,
      password: this.password
    }
  }
  console.log(`${test.credentials.username}`)

  this.$http.post(`http://localhost:8080/user/users/delete/@${this.username}`, test).then(
    this.localStorageService.remove('username'),
    this.localStorageService.remove('password'),
    this.localStorageService.remove('isAuthenticated'),
    this.$state.transitionTo('login')
  )
//   this.$http({
//     method: 'DELETE',
//     url: `http://localhost:8080/user/users/@${this.username}`,
//     data: test
// }).then((response) => {
//     return true
//   }, (response) => {
//     console.log(`${response.data} and ${response.status} config ${response.config.data.credentials.username} ${response.config.data.credentials.password}`)
//     this.error = 'Username or password are incorrect, please try again.'
//   })
}
deleteUser2 () {
  let username = this.localStorageService.get('username')
  let password = this.localStorageService.get('password')
  this.$http({
  method: 'POST',
  url: 'http://localhost:8080/user/users/@' + username,
  data: {credentials: { password: password, username: username }}
}).then(this.successCallback = (response) => {
   alert('Liked!!')
}, this.errorCallback = (response) => {
  this.error = 'Username or password are incorrect, please try again.'
})

}
logout () {
  this.localStorageService.remove('username')
  this.localStorageService.remove('password')
  this.localStorageService.remove('isAuthenticated')
  this.localStorageService.remove('author')
  this.$state.transitionTo('login')
}
}

export const ftUserSettings = {
  controller,
  templateUrl,
  controllerAs: 'usersettings',
  bindings: {
    username: '@',
    password: '@'
  }
}
