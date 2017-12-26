package hhc.common.utils.http;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

/**
 * Created by huhuichao on 2017/7/27.
 */
public class HtmlUtil {


    /**
     * 将html中的标签去掉返回文本
     * @param htmlContent
     * @return
     */
    public static String html2String(String htmlContent){
        if(StringUtils.isNotEmpty(htmlContent)){
            return Jsoup.clean(htmlContent, Whitelist.none()).replaceAll("&gt;","");
        }
        return "";
    }

    public static void main(String[] args) {

        String htmlContent="<div class='container'><h1 class='tt-h1' >周杰伦前任“侯佩岑”PK少男杀手“蔡依林”，究竟谁的护肤秘诀完胜呢？</h1> <div class='tt-div' > <span class='tt-span' > AlwaysChic </span> <span class='tt-span' >2017-07-19 22:28</span> </div> <div class='tt-div' > <div class='tt-div' > <blockquote> <p class='tt-p'><strong>护肤是所有女生都在做的事情,很多明星的作息时间都不准确,但是她们却可以保持相当完美的肌肤,你知道是为什么吗?</strong></p> <p class='tt-p'><strong>下面我们来看看几位女星的护肤技巧吧~</strong></p> </blockquote> <p class='tt-p'><img class='tt-img' src='http://p1.pstatp.com/large/2ee20000a293c8cf59a9' alt='周杰伦前任“侯佩岑”PK少男杀手“蔡依林”，究竟谁的护肤秘诀完胜呢？' inline='0'></p> <p class='tt-p'><strong>蔡依林</strong></p> <p class='tt-p'><strong>妆前面膜</strong></p> <p class='tt-p'>蔡依林表示，每天都要化妆，妆前面膜很重要，皮肤很容易受到侵害，角质也容易堆积，所以，她每天早上醒来都会使用面膜，这样让妆容更加服帖，还能加快皮肤新陈代谢。</p> <p class='tt-p'><img class='tt-img' src='http://p3.pstatp.com/large/2ed600044f97643928af' alt='周杰伦前任“侯佩岑”PK少男杀手“蔡依林”，究竟谁的护肤秘诀完胜呢？' inline='0'></p> <p class='tt-p'><strong>林志玲</strong></p> <p class='tt-p'><strong> 最好的保养，就是让皮肤休息</strong></p> <p class='tt-p'>让皮肤好好休息，有时过多的保养品，反倒是负担。注重运动的她每天早上起床都会做一些健身操，踩登山机、摇呼拉圈、练抬腿，帮助振奋精神。</p> <p class='tt-p'>皮肤护理每天不管再忙、再累，都会泡澡，顺便利用泡澡时的蒸气趁机护发和敷脸，或在洗澡后，用事先放在冰箱里的化妆水敷脸。化妆水有镇定皮肤的作用，再加 上适度的降温，可以帮助收缩毛孔。此外，林美人睡前会按摩眼睛与胸部穴道进行保养，洗脸后用冰块敷脸30秒让肌肤保持紧实。</p> <p class='tt-p'><strong>梁静茹 </strong></p> <p class='tt-p'><strong>燕尾碟的精油物语</strong></p> <p class='tt-p'>在肌肤护理上，她最关注的就是“补水”， “补水”、“净化”和“滋养'三者并行的新概念。梁静茹说：精油真的很神奇，用精油泡澡可以缓解疲劳，香橙味道的精油能让人心情愉悦，而熏衣草能带来好的睡眠。她还说自己在家中就经常尝试不同的配方，既能放松身心还能排除身体内的毒素，可谓一举两得。最简单实用的就是香熏用法，整个房间都充盈着精油的味道，这个时候她就会放上一张小野丽莎的碟片，感觉整个人都彻底放松。</p> <p class='tt-p'><img class='tt-img' src='http://p3.pstatp.com/large/2ed600044eea063b2a72' alt='周杰伦前任“侯佩岑”PK少男杀手“蔡依林”，究竟谁的护肤秘诀完胜呢？' inline='0'></p> <p class='tt-p'><strong>侯佩岑：拉提眼霜保养眼周肌肤</strong></p> <p class='tt-p'>跟周董透明化的恋情，只可惜没有在对的时间遇上对的人，这位美女主播的眼睛可谓是一大亮点，所以她本身也注重保养眼周肌肤。侯佩岑说：“我最在意眼睛了，因为眼皮比较多层，看起来容易无神，后来很认真的擦拉提眼霜，且绝对不用来回涂抹的方法，只以指腹轻轻拍到完全吸收为止。”</p> <p class='tt-p'><img class='tt-img' src='http://p1.pstatp.com/large/2ed700044e7d3622d8b7' alt='周杰伦前任“侯佩岑”PK少男杀手“蔡依林”，究竟谁的护肤秘诀完胜呢？' inline='0'></p> <p class='tt-p'><strong>刘诗诗护肤美白秘诀</strong></p> <p class='tt-p'>在出门前的十五分钟涂抹防晒霜，这样才能保证跟肌肤的服帖性。分区域防晒，针对紫外线对各个部位的皮肤不同伤害，护理的需求也不同。比如面部皮肤分泌较高，颈部汗多，身体各个部位的问题也不同，选用专业的防晒护肤品分别为面部和身体专门呵护。多吃能够美白的水果，当然也包括抗晒，比如：哈密瓜、樱桃、西瓜、西红柿等，皮肤吸收VC美白营养素、抗防晒成份，皮肤自然晒不黑。</p> </div> </div> <div class='tt-div' > <div class='tt-div' > <i ></i> <ul class='tt-ul'> > <li class='tt-li'> ><a href='/search/?keyword=护肤' target='_blank'>护肤</a></li> <li class='tt-li'> ><a href='/search/?keyword=侯佩岑' target='_blank'>侯佩岑</a></li> <li class='tt-li'> ><a href='/search/?keyword=美容' target='_blank'>美容</a></li> <li class='tt-li'> ><a href='/search/?keyword=面膜' target='_blank'>面膜</a></li> </ul> </div> <div class='tt-div' riot-tag='actionBox' > <div class='tt-div' > <i ></i> <span class='tt-span' >收藏</span> </div> <a href='javascript:;' > <i ></i><span class='tt-span' >举报</span> </a> <div class='tt-div' ></div> </div> </div><div>";
        Document doc = Jsoup.parseBodyFragment(htmlContent);
        Element body = doc.body();
        System.out.println(body);

        htmlContent = Jsoup.clean(htmlContent, Whitelist.none()).replaceAll("&gt;","");
        System.out.println(htmlContent);
    }
}
