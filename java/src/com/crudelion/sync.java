package com.crudelion;


public class sync {
    public static void main(String[] args) {
        XmlReader xmlReader = null;
        try {
            xmlReader = new XmlReader(args[0]);
        } catch (ConfigException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("同步源目录:" + xmlReader.getSource() + "目标" + xmlReader.getTarget());
        System.out.println("源目录排除:" + xmlReader.getSourceExclude());
        System.out.println("目标清除属性:" + xmlReader.getTargetClean());
        if (!xmlReader.getTargetClean()) {
            System.out.println("目标目录排除:" + xmlReader.getTargetExclude());
        }
        FileSync fileSync = new FileSync();
        fileSync.sync(xmlReader.getSource(), xmlReader.getTarget(), xmlReader.getSourceExclude(), xmlReader.getTargetExclude());
    }
}
