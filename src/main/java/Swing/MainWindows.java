package Swing;

import org.json.JSONObject;
import xyz.xingfeng.www.pixiv.SouSuo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainWindows extends JFrame {
    private final JTextField tagSelect;
    private final JTextField goodScreen;
    private final JComboBox<String> age;
    private final JTextField ip;
    private final JTextField port;
    public static JButton jB1 = new JButton();
    /**
     * 窗口大小
     */
    private int fcHeight = 330;
    private int fcWidth = 500;

    /**
     * 动态标签，用于计算已下载图片数量
     */
    public static JLabel j = new JLabel();
    public static int num = 0;


    public MainWindows(){
        //加载logo
        ImageIcon icon = new ImageIcon("img/DT.png");
        // 设置窗口图标
        setIconImage(icon.getImage());
        //设置标题，名称
        setTitle("pixiv爬虫程序");
        // 获取屏幕宽高
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 设置窗口位置大小
        setBounds(scrSize.width / 2 - fcWidth / 2, scrSize.height / 2 - fcHeight / 2, fcWidth, fcHeight);
        // 获取布局
        Container cn = this.getContentPane();
        // 取消布局
        cn.setLayout(null);

        //构建常用字体
        Font st = new Font("宋体", 20, 15);
        Font font = st.deriveFont(Font.BOLD);
        //创建窗口中常量组件
        JLabel jL1 = new JLabel("<html>搜索标签:</html>",SwingConstants.CENTER);
        JLabel jL2 = new JLabel("<html>点赞筛选:</html>",SwingConstants.CENTER);
        JLabel jL3 = new JLabel("<html>选择年龄阶段:</html>",SwingConstants.CENTER);

        j.setFont(font);
        j.setBounds(50,220,fcWidth,50);
        cn.add(j);


        jL1.setFont(font);
        jL1.setBounds(36,35,100,50);

        jL2.setFont(font);
        jL2.setBounds(36,85,100,50);

        jL3.setFont(font);
        jL3.setBounds(240,85,120,50);

        cn.add(jL1);
        cn.add(jL2);
        cn.add(jL3);

        //构建输入框对象
        //标签搜索
        tagSelect = new JTextField();
        tagSelect.setBounds(136,43,200,34);
        tagSelect.setFont(font);
        cn.add(tagSelect);
        //点赞筛选
        goodScreen = new JTextField();
        goodScreen.setBounds(136,93,100,34);
        goodScreen.setFont(font);
        cn.add(goodScreen);


        //按钮
        jB1 = new JButton("开始爬取");
        jB1.setFont(font);
        jB1.setBounds(350,155,100,50);
        jB1.addActionListener(new AListener());
        cn.add(jB1);


        //年龄阶段
        String[] ages = {"全年龄","R18","所有"};
        age = new JComboBox<>(ages);
        age.setFont(font);
        age.setBounds(360,95,75,30);
        cn.add(age);

        //代理设置窗口
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 310));
        layeredPane.setBorder(BorderFactory.createTitledBorder(
                "代理设置"));

        layeredPane.setBounds(45,143,300,70);
        cn.add(layeredPane);
        //代理ip
        JLabel jL4 = new JLabel("<html>ip:</html>",SwingConstants.CENTER);
        JLabel jL5 = new JLabel("<html>端口:</html>",SwingConstants.CENTER);
        ip = new JTextField();
        port = new JTextField();

        jL4.setFont(font);
        jL4.setBounds(4,22,50,30);
        ip.setFont(font);
        ip.setBounds(55,22,100,30);
        layeredPane.add(ip);
        layeredPane.add(jL4);

        jL5.setFont(font);
        jL5.setBounds(155,22,50,30);
        port.setFont(font);
        port.setBounds(205,22,60,30);
        layeredPane.add(port);
        layeredPane.add(jL5);


        port.setFont(font);

        // 是否显示窗口体
        setVisible(true);
        // 窗体不能修改大小
        setResizable(false);
        // 窗体关闭后自动结束后台
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //加载保存的数据
        try {
            read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void read() throws IOException {
        File file = new File("config.json");
        BufferedReader bufferedReader = null;
        if (file.exists()){
            String s;
            bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder st = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null){
                 st.append(s);
            }
            JSONObject jsonObject = new JSONObject(st.toString());
            tagSelect.setText(jsonObject.getString("tagSelectText"));
            goodScreen.setText(String.valueOf(jsonObject.getInt("finalGoodNum")));
            ip.setText(jsonObject.getString("ip"));
            port.setText(jsonObject.getString("port"));
        }
    }

    class AListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String ip = MainWindows.this.ip.getText();
            String port = MainWindows.this.port.getText();
            String tagSelectText = tagSelect.getText();
            String good = goodScreen.getText();
            int goodNum = 0;
            if (!good.equals("")){
                goodNum = Integer.parseInt(good);
            }else {
                return;
            }
            String type = SouSuo.SAFE;
            String selectedItem = (String) age.getSelectedItem();
            switch (selectedItem){
                case "全年龄":
                    type = SouSuo.SAFE;
                    break;
                case "R18":
                    type = SouSuo.R18;
                    break;
                case "所有":
                    type = SouSuo.ALL;
                    break;
            }
            String finalType = type;
            int finalGoodNum = goodNum;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new LoadCrawler(ip,port,tagSelectText, finalType, finalGoodNum);
                }
            }).start();
            //保存这次提供的信息
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ip",ip);
            jsonObject.put("port",port);
            jsonObject.put("tagSelectText",tagSelectText);
            jsonObject.put("finalType",finalType);
            jsonObject.put("finalGoodNum",finalGoodNum);
            try {
                save(jsonObject);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            j.setText("累计下载:"+num);
            jB1.setEnabled(false);

        }
    }

    public void save(JSONObject jsonObject) throws IOException {
        File file = new File("config.json");
        FileWriter fileWriter = null;
        if (file.exists()){
            //文件存在
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toString());
        }else {
            //文件不存在
            //创建新文件
            if (file.createNewFile()){
                fileWriter = new FileWriter(file);
                fileWriter.write(jsonObject.toString());
            }
        }
        if (fileWriter != null){
            fileWriter.close();
        }
    }
}
