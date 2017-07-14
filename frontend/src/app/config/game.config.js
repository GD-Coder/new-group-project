export const gameConfig =
  ($stateProvider) => {
    'ngInject'
    $stateProvider.state({
      name: 'game',
      url: '/game',
      component: 'ftGame'
    })
  }
