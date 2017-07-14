import 'app/styles/app.styles'
import templateUrl from 'app/html/app.template'

const controller = class FtAppController {
  constructor ($log) {
    'ngInject'
    $log.debug('ft-app is a go')
  }
}

export const ftApp = {
  controller,
  templateUrl,
  controllerAs: 'app'
}
