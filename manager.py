#!/usr/bin/python
# -*- coding: utf-8 -*-
import sys
import os 
import os.path 
import shutil
from xml.dom import minidom

class UnicodeStreamFilter:  
    def __init__(self, target):  
        self.target = target  
        self.encoding = 'utf-8'  
        self.errors = 'replace'  
        self.encode_to = self.target.encoding  
    def write(self, s):  
        if type(s) == str:  
            s = s.decode("utf-8")  
        s = s.encode(self.encode_to, self.errors).decode(self.encode_to)  
        self.target.write(s)  
          
if sys.stdout.encoding == 'cp936':  
    sys.stdout = UnicodeStreamFilter(sys.stdout)

'''拷贝文件'''
def copyFile(sourceFile, targetFile):
	cmd_delete = "rm -f " + targetFile
	print(cmd_delete)
	os.system(cmd_delete)
	cmd_copy = "cp " + sourceFile + " " + targetFile
	print(cmd_copy)
	os.system(cmd_copy)
	'''
	if(os.path.isfile(targetFile)):
		os.remove(targetFile);
	if(os.path.isfile(sourceFile)):
		open(targetFile, "wb").write(open(sourceFile, "rb").read()) 
	'''

def parseAntProject():
	dom = minidom.parse('build.xml')
	root = dom.documentElement
	return root.getAttribute("name")

#解析Android项目的xml文件，获得versionCode,versionName,package,main
def parseAndroidManifest():
        ACTION_MAIN = "android.intent.action.MAIN"
        dom = minidom.parse('AndroidManifest.xml')
        root = dom.documentElement
        result = {}
        result["versionCode"] = root.getAttribute("android:versionCode")
        result["versionName"] = root.getAttribute("android:versionName")
        result["package"] = root.getAttribute("package")
        for keys in root.attributes.keys():
                if(root.attributes[keys].name == "android:versionCode"):
                        result["versionCode"] = root.attributes[keys].value
                elif(root.attributes[keys].name == "android:versionName"):
                        result["versionName"] = root.attributes[keys].value
                elif(root.attributes[keys].name == "package"):
                        result["package"] = root.attributes[keys].value
        for activity in root.getElementsByTagName("activity"):
                if(activity.nodeName == "activity"):
                        for action in activity.getElementsByTagName("action"):
                                if(action.getAttribute("android:name") == ACTION_MAIN):
                                        result["main"] = activity.getAttribute("android:name")
	main = result["main"]
	package = result["package"]
	if(main.startswith(package)):
		index = main.find(package)
		result["main"] = main[len(package)+index]
	elif(main.startswith(".") == False):
		result["main"] = "." + main

        return result

def isInstall(package):
	cmd_app_exist = "adb shell ls data/app/" + " | grep " +  package
	result = os.popen(cmd_app_exist).readlines()
	if(result):
		return True
	else:
		return False

#初始化项目的接口，下载gitmodule
def init():
	print('开始下载其它依赖模块')
	cmd_download_denpency = "git submodule update --init"
	print(cmd_download_denpency)
	os.system(cmd_download_denpency)

	print('进行相同的库同步')
	sourceFile = "libs/android-support-v4.jar"
	targetFile = "contrib/ActionBarSherlock/library/libs/android-support-v4.jar"
	copyFile(sourceFile, targetFile)

	print('进行依赖项目更新')
	cmd_upate_actionbarsherlock = "android update project -p contrib/ActionBarSherlock/library/ -t android-15 "
	print(cmd_upate_actionbarsherlock)
	os.system(cmd_upate_actionbarsherlock)
	cmd_upate_undergarment = "android update project -p contrib/Slidingmenu/library "
	print(cmd_upate_undergarment)
	os.system(cmd_upate_undergarment)
	cmd_upate_betweenus = "android update project -p ./ -t android-15 "
	print(cmd_upate_betweenus)
	os.system(cmd_upate_betweenus)

	'''
	print('拷贝其它库到本地目录')
	sourceFile = "contrib/weibo_android_sdk/weibo.sdk.android.sso.jar"
	targetFile = "libs/weibo.sdk.android.sso.jar"
	copyFile(sourceFile,targetFile)
	'''

def compile(arg):
	cmd_compile = "ant " + arg
	print(cmd_compile) 
	os.system(cmd_compile)

def install(package):
	debug_apk = "bin/" + parseAntProject() + "-debug.apk" 
	if(os.path.exists(debug_apk) == False):
		compile("debug")
	if(isInstall(package) == True):
		uninstall(package)
	cmd_install = "adb install " + debug_apk
	print(cmd_install)
	os.system(cmd_install)

def uninstall(package):
	cmd_uninstall = "adb uninstall " + package
	print(cmd_uninstall)
	os.system(cmd_uninstall)

def launch(package,activity):
	cmd_launch = "adb shell am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n " + package + "/" + activity
	print(cmd_launch)
	os.system(cmd_launch)
def run(package, activity):
	install(package)
	launch(package,activity)

def help():
	print("Usage: manager.py <argument>")
	print("\t arguments:");
	print("\t\t init 初始化本项目的所有依赖模块")
	print("\t\t debug 编译项目的debug版本")
	print("\t\t release 编译项目的release版本")
	print("\t\t install 安装项目的debug版本，当项目没有编译时会自动编译，假设apk已安装会重新安装")
	print("\t\t uninstall 卸载项目")
	print("\t\t run 编译运行安装项目的debug版本")
	print("\t\t help 帮助")

if  __name__ =="__main__": 
	if(len(sys.argv) != 2):
		print("参数错误")
		#打印帮助
		help()
		exit()
	if(sys.argv[1] == "init"):
		init()
	elif(sys.argv[1] == "debug"):
		compile("debug")
	elif(sys.argv[1] == "release"):
		compile("genall")
	elif(sys.argv[1] == "install"):
		appinfo = parseAndroidManifest()
		package = appinfo["package"]
		activity = appinfo["main"]
		install(package)
	elif(sys.argv[1] == "uninstall"):
		appinfo = parseAndroidManifest()
		package = appinfo["package"]
		uninstall(package)
	elif(sys.argv[1] == "run"):
		appinfo = parseAndroidManifest();
		package = appinfo["package"]
		activity = appinfo["main"]
		run(package, activity)
	else:
		print("错误的参数")
		help()

