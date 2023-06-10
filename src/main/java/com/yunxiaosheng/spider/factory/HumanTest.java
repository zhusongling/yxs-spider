package com.yunxiaosheng.spider.factory;

public class HumanTest {
    public static void main(String[] args) {
        AbstractHumanFactory humanFactory = new HumanFactory();

        WhiteHuman whiteHuman = humanFactory.createHuman(WhiteHuman.class);
        whiteHuman.getColor();
        whiteHuman.talk();

        System.out.println("-----------------------------------");

        YellowHuman yellowHuman = humanFactory.createHuman(YellowHuman.class);
        yellowHuman.getColor();
        yellowHuman.talk();

        System.out.println("-----------------------------------");

        BlackHuman blackHuman = humanFactory.createHuman(BlackHuman.class);
        blackHuman.getColor();
        blackHuman.talk();


    }
}
