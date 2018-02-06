# 自定义ProgressBar

简图：

![](http://ohazfcl3s.bkt.clouddn.com/progressbar.gif)

## 采用kotlin编写的带进度的ProgressBar



    <declare-styleable name="HorizontalProgress">
        <attr name="progress_reach_color" />
        <attr name="progress_reach_height" />
        <attr name="progress_unreach_color" />
        <attr name="progress_unreach_height" />
        <attr name="progress_text_color" />
        <attr name="progress_text_size" />
        <attr name="progress_text_offset" />
    </declare-styleable>
    
    <declare-styleable name="RoundProgress">
        <attr name="progress_reach_color" />
        <attr name="progress_reach_height" />
        <attr name="progress_unreach_color" />
        <attr name="progress_unreach_height" />
        <attr name="progress_text_color" />
        <attr name="progress_text_size" />
        <attr name="progress_text_offset" />
        <attr name="progress_radius"/>
    </declare-styleable>
    
    
    包括横向的进度条和圆形的进度条，如上所示的属性可以进行再布局中进行修改
    
### 本项目仅做参考，是学习自定义View的一些笔记。