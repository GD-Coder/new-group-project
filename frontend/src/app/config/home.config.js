export const homeConfig =
  ($stateProvider) => {
    'ngInject'
    $stateProvider.state({
      name: 'home',
      url: '/home',
      component: 'ftHome'
    })
  }
