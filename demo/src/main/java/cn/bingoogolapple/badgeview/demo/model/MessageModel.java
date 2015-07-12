package cn.bingoogolapple.badgeview.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/7/12 上午11:33
 * 描述:
 */
public class MessageModel {
    public String title;
    public String detail;
    public int count;

    public MessageModel(String title, String detail, int count) {
        this.title = title;
        this.detail = detail;
        this.count = count;
    }

    public static List<MessageModel> getTestDatas() {
        List<MessageModel> messages = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            messages.add(new MessageModel("title" + i, "detail" + i, random.nextInt(150)));
        }
        return messages;
    }

}