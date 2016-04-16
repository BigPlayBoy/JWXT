package mail;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
 
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
 

public class ProductPriceObserver implements Observer {
 
    @Override
    public void update(Observable obj, Object arg) {
    Product product = null;
    if (obj instanceof Product) {
        product = (Product) obj;
    }
    if (arg instanceof Float) {
        Float price = (Float) arg;
        Float decrease = product.getPrice() - price;
        if (decrease > 0) {
        // 发送邮件
        SimpleMailSender sms = MailSenderFactory
            .getSender(MailSenderType.SERVICE);
        List<String> recipients = new ArrayList<String>();
        recipients.add("invisible@qq.com");
        recipients.add("invisible@gmail.com");
        try {
            for (String recipient : recipients) {
            sms.send(recipient, "价格变动", "您关注的物品"
                + product.getName() + "降价了，由"
                + product.getPrice() + "元降到" + price + "元，降幅达"
                + decrease + "元人民币。赶快购物吧。");
            }
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        }
    }
    }
 
}