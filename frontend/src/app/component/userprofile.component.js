import 'app/styles/userprofile.styles'
import templateUrl from 'app/html/userprofile.template'

const controller = class FtProfileController {
  constructor ($log, $state, $location, $http, ftGameSettings, $window, $rootScope, appService, localStorageService) {
    'ngInject'
    this.localStorageService = localStorageService
    this.service = appService
    this.settings = ftGameSettings
    this.$location = $location
    this.$window = $window
    this.$state = $state
    this.$http = $http
    this.loadNewTweets()
    this.getInfo()
    //$rootScope.$on(this.$location.$routeChangeStart, this.checkAuth())
    $log.log('ft-profile is a go')
  }
  // checkAuth () {
  //   let isauthenticated = this.localStorageService.get('isAuthenticated')
  //   if(isauthenticated) {
  //     this.$state.transitionTo('home')
  //   }
  // }

  loadNewTweets () {
    let author = this.localStorageService.get('author')
    this.$http({
    method: 'GET',
    url: 'http://localhost:8080/user/users/@' + author + '/feed'
  }).then(this.successCallback = (response) => {
     this.tweets = []
     this.tweets = response.data
  }, this.errorCallback = (response) => {
    this.error = 'Username or password are incorrect, please try again.'
  })

  }
  getInfo () {
    let author = this.localStorageService.get('author')
    this.$http({
    method: 'GET',
    url: 'http://localhost:8080/user/users/@' + author
  }).then(this.successCallback = (response) => {
     this.fname = response.data['profile']['firstName']
     this.lname = response.data['profile']['lastName']
     this.email = response.data['profile']['email']
     this.phone = response.data['profile']['phone']
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

export const ftProfile = {
  controller,
  templateUrl,
  controllerAs: 'profile'
}
