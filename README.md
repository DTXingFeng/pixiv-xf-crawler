# Pixiv搜索爬虫
基于java的pixiv爬虫，使用cookie进行登录操作，下载图片
# 主要功能
1. 获得pixiv搜索页面的图片id  
2. 下载指定id图片至**本地**  
3. 可通关**点赞数**进行图片筛选  
4. 可使用**标签**进行搜索
5. 可进行**年龄分级**的选择
# 特色
1. 使用java开发的本地爬虫软件
# 使用须知
1. 登录pixiv后将**cookie**复制到**cookie.txt**文件中，本程序使用cookie进行登录
2. data文件是用来记录已经下载，或已经被筛选的图片
3. 下载的图片会储存到根目录下的Pixiv文件夹内
## 搜索页api
https://www.pixiv.net/ajax/search/artworks/ <kbd>搜索内容</kbd>?p=<kbd>页数</kbd>&s_mode=s_tag_full&mode=<kbd>限制级</kbd>&lang=<kbd>语言类型 中文是zh</kbd>  
示例：
https://www.pixiv.net/ajax/search/artworks/nahida?&p=2&s_mode=s_tag_full&mode=safe&lang=zh  
此api无需请求头
## 下载链接
https://i.pximg.net/img-original/img/+ <kbd>(图片发布时间的)年/月/日/时/分/秒/</kbd>+<kbd>图片的文件名</kbd>  
需要请求头
## 应用截图
![展示图片](https://github.com/DTXingFeng/pixiv-xf-crawler/blob/main/tp.png)
