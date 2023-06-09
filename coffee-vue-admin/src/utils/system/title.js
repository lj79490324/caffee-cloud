import i18n from '@/locale'
import { systemTitle } from '@/config'
const { t } = i18n.global

export function changeTitle(name) {
  document.title = `${t(name)}-${t(systemTitle)}`
}
