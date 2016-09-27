

主要用到的有Spanned ClickSpan ImageSpan ImagerGetter Html 。
先看一下效果图：
![这里写图片描述](http://img.blog.csdn.net/20160927214843959)

我把它封装成了一个控件，使用的时候只要将它放到xml布局

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.wingsofts.richtextview.MainActivity"
    >

  <com.wingsofts.richtextview.RichTextView
      android:id="@+id/richTextView"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      />
</RelativeLayout>

```

，然后

```
  RichTextView richTextView = (RichTextView) findViewById(R.id.richTextView);
    richTextView.setHtml(mTxt,1000,800);
    richTextView.setOnImageClickListener(new RichTextView.ImageClickListener() {
      @Override public void onImageClick(String imageUrl, String[] imageUrls, int position) {
        Toast.makeText(MainActivity.this, "imageUrl :"+imageUrl+"\nimage size:"+imageUrls.length+"\n position:"+position, Toast.LENGTH_SHORT).show();
      }
    });
```




###实现思路

由于后台是传来的html，所以可以借助系统类Html来解析生成Spanned，再将SpannedString转换为ClickSpan，最终实现图文混排+图片交互效果。

后台先传来一段html，如下：

```
String mTxt =
      "<p>\r\n\t<span style=\"font-size:16px;\"><strong>比腾讯还土豪 传《阴阳师》项目组发60个月工资奖金</strong></span>\r\n</p>\r\n<p>\r\n\t<span style=\"font-size:16px;\">　　今日下午一则关于网易《阴阳师》项目组员工发60个月工资的奖金忽然在整个游戏圈流传，而以网易游戏平均10000以上的薪资水平来算，《阴阳师》项目组成员的奖金将达到60万元以上。"
          + "<img src=\"http://p2.pstatp.com/large/e220006a85a0b689eb8\" width=\"520\" height=\"216\" title=\"上证指数\" alt=\"上证指数\" />在游戏公司项目组发奖金较为平常，不过能够达到60个月工资的也就此前盛传的腾讯LOL项目组曾经打到过，包括几年之前被业界津津乐道的CF项目组都未曾有过这么高的规格。根据多家网站的数据和行业平均水平，网易游戏的平均薪资应该不会低于10000，这笔奖金的总额度可能创游戏行业有史以来最高。"
          + "</span>\r\n</p>\r\n<p>\r\n\t<span style=\"font-size:16px;\"><br />\r\n</span>\r\n</p>\r\n<p>\r\n\t<span style=\"font-size:16px;\"><img src=\"http://p3.pstatp.com/large/e1d000f89d603327470\" width=\"520\" height=\"216\" title=\"上证指数\" alt=\"上证指数\" />《阴阳师》是网易自研的3D和风卡牌RPG手游，同时该作也是一款二次元向手游，游戏9月2正式上架App Store，9月9日开始全平台公测，自上架以来该作就开始了传奇的冲榜之旅，到今天已经高居畅销榜第二名，仅次于长期包揽第一的同门师兄《梦幻西游》。\n"
          + "\n"
          + "<img src=\"http://p3.pstatp.com/large/e21000f51e83cb9b1c9\" width=\"520\" height=\"216\" title=\"上证指数\" alt=\"上证指数\" />";

```

之后利用Html.form()生成Spanned

```
stringBuilder = (SpannableStringBuilder) Html.fromHtml(source,
        new GlideImageGetter(mContext, Glide.with(mContext), this, false, width, height), null);

```

注意这里的第二个参数，是一个ImageGetter类型的接口，这里**直接拿了Glide作者开发**的Imagegetter来使用，他的作用是在spanned中加载图片。

国内的文章大多介绍到这里，实现了图文混排。。。然后就没有然后了。。可是尼玛我想点击放大啊有没有。。   莫急，接下来就告诉你们，如何点击交互。

其实就是将Spanned转换为clickspan，

```
//从stringBuilder中读取图片
mImageSpans = stringBuilder.getSpans(0, stringBuilder.length(), ImageSpan.class);

	 //过滤出整个textView的所有图片
    mImageUrls = new String[mImageSpans.length];
    for (int i = 0; i < mImageSpans.length; i++) {
      mImageUrls[i] = mImageSpans[i].getSource();
    }
    for (int i = 0; i < mImageSpans.length; i++) {

		//获取图片span的起尾
      int start = stringBuilder.getSpanStart(mImageSpans[i]);
      int end = stringBuilder.getSpanEnd(mImageSpans[i]);
      final int finalI = i;
      
      //将span转化为clickspan
      stringBuilder.setSpan(new ClickableSpan() {
        @Override public void onClick(View widget) {
          if (mImageClickListener != null) {
            mImageClickListener.onImageClick(mImageUrls[finalI], mImageUrls, finalI);
          }
        }
      }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    
```

嘛。。理论上这样就可以了，可是事实上，你会发现，点击图片根本没鸟反应！！！！！然后我有鼓捣了一会。。发现需要加上一句,才可以。
```

    setMovementMethod(LinkMovementMethod.getInstance());
    
```
