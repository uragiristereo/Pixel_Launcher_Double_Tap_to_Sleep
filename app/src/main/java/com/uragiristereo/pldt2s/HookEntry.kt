package com.uragiristereo.pldt2s

import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import com.topjohnwu.superuser.Shell

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {
    companion object {
        const val PACKAGE_NAME = "com.google.android.apps.nexuslauncher"
        private const val workspaceTouchListenerClass = "com.android.launcher3.touch.WorkspaceTouchListener"
    }

    override fun onInit() = configs {
        isDebug = false
    }

    override fun onHook() = encase {
        loadApp(name = PACKAGE_NAME) {
            workspaceTouchListenerClass.hook {
                injectMember {
                    method {
                        name = "onDoubleTap"
                        superClass()
                    }

                    afterHook {
                        Shell.cmd("input keyevent 26").exec()
                    }
                }
            }
        }
    }
}