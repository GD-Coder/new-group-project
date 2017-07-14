export const defaultPointSettings = {
  increment: 1,
  modifier: {
    amount: 0.2,
    cost: 10
  },
  autoclicker: {
    interval: 1,
    cost: 100
  },
  baseincrement: 1
}
export const defaultGameSettings = {
  total: 0,
  modifiers: 0,
  autoclickers: 0
}
export const userInfo = {
  name: undefined,
  username: 'admin',
  password: 'admin',
  isAuthenticated: false
}
export const user = {
  firstname: undefined,
  lastname: undefined,
  password: undefined,
  username: undefined,
  email: undefined
}
export const wallpaperSettings = {
  metal: 'url(/images/bg5.jpg)',
  antithesis: 'url(/images/bg4.jpg)',
  blue: 'url(/images/bg6.jpg)',
  warped: 'url(/images/bg7.jpg)',
  current: undefined
}
export const ftGameSettings = {
  defaultPointSettings,
  defaultGameSettings,
  wallpaperSettings,
  user,
  userInfo
}
