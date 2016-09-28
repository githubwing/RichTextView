package com.wingsofts.richtextviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.wingsofts.richtextview.RichTextView;

public class MainActivity extends AppCompatActivity {
  String mTxt =
      "<p>\r\n\t<span style=\"font-size:16px;\"><strong>比腾讯还土豪 传《阴阳师》项目组发60个月工资奖金</strong></span>\r\n</p>\r\n<p>\r\n\t<span style=\"font-size:16px;\">　　今日下午一则关于网易《阴阳师》项目组员工发60个月工资的奖金忽然在整个游戏圈流传，而以网易游戏平均10000以上的薪资水平来算，《阴阳师》项目组成员的奖金将达到60万元以上。"
          + "<img src=\"http://p2.pstatp.com/large/e220006a85a0b689eb8\" width=\"520\" height=\"216\" title=\"上证指数\" alt=\"上证指数\" />在游戏公司项目组发奖金较为平常，不过能够达到60个月工资的也就此前盛传的腾讯LOL项目组曾经打到过，包括几年之前被业界津津乐道的CF项目组都未曾有过这么高的规格。根据多家网站的数据和行业平均水平，网易游戏的平均薪资应该不会低于10000，这笔奖金的总额度可能创游戏行业有史以来最高。"
          + "</span>\r\n</p>\r\n<p>\r\n\t<span style=\"font-size:16px;\"><br />\r\n</span>\r\n</p>\r\n<p>\r\n\t<span style=\"font-size:16px;\"><img src=\"http://p3.pstatp.com/large/e1d000f89d603327470\" width=\"520\" height=\"216\" title=\"上证指数\" alt=\"上证指数\" />《阴阳师》是网易自研的3D和风卡牌RPG手游，同时该作也是一款二次元向手游，游戏9月2正式上架App Store，9月9日开始全平台公测，自上架以来该作就开始了传奇的冲榜之旅，到今天已经高居畅销榜第二名，仅次于长期包揽第一的同门师兄《梦幻西游》。\n"
          + "\n"
          + "<img src=\"http://p3.pstatp.com/large/e21000f51e83cb9b1c9\" width=\"520\" height=\"216\" title=\"上证指数\" alt=\"上证指数\" />";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RichTextView richTextView = (RichTextView) findViewById(R.id.richTextView);
    richTextView.setHtml(mTxt, 500);
    richTextView.setOnImageClickListener(new RichTextView.ImageClickListener() {
      @Override public void onImageClick(String imageUrl, String[] imageUrls, int position) {
        Toast.makeText(MainActivity.this, "imageUrl :"
            + imageUrl
            + "\nimage size:"
            + imageUrls.length
            + "\n position:"
            + position, Toast.LENGTH_SHORT).show();
      }
    });
  }
}
