export function config ($logProvider, $locationProvider, $urlRouterProvider) {
  'ngInject'
  $logProvider.debugEnabled(true)
  $locationProvider.html5Mode(true)
  $urlRouterProvider.otherwise('/login')
}
