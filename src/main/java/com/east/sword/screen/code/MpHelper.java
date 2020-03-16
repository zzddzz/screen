package com.east.sword.screen.code;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 代码生成器
 * @CreateDate 22:40 2020/2/19.
 * @Author ZZD
 */
@Slf4j
public class MpHelper {

    private String outPutDir = "D://";
    private String dbUserName;
    private String dbPassword;
    private String dbUrl;
    private String moduleName = "test";
    private boolean fileOverride = true;
    private String[] includeTable;
    private String[] excludeTable;

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public void setIncludeTable(String[] includeTable) {
        this.includeTable = includeTable;
    }

    public void setExcludeTable(String[] excludeTable) {
        this.excludeTable = excludeTable;
    }

    /**
     * 引用用参数类
     * @param outPutDir 生成代码的位置
     * @param dbUserName 数据库用户名
     * @param dbPassword 数据库密码
     * @param dbUrl 数据库ＵＲＬ
     * @param moduleName　生成代码的所属包的名称
     */
    public MpHelper(String outPutDir, String dbUserName, String dbPassword, String dbUrl, String moduleName ) {
        this.outPutDir = outPutDir;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
        this.dbUrl = dbUrl;
        this.moduleName = moduleName;
    }

    public void execute() {
        if (StringUtils.isEmpty(this.dbUserName)) {
            log.error("数据库连接信息不能为空");
        } else {
            AutoGenerator mpg = new AutoGenerator();
            GlobalConfig gc = new GlobalConfig();
            gc.setOutputDir(this.outPutDir);
            gc.setFileOverride(true);
            gc.setActiveRecord(true);
            gc.setEnableCache(false);
            gc.setBaseResultMap(false);
            gc.setBaseColumnList(false);
            gc.setAuthor("ZZD");
            mpg.setGlobalConfig(gc);
            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setDbType(DbType.MYSQL);
            dsc.setTypeConvert(new MySqlTypeConvert() {
                public DbColumnType processTypeConvert(String fieldType) {
                    return super.processTypeConvert(fieldType);
                }
            });
//         dsc.setDriverName("com.mysql.cj.jdbc.Driver");  //mysql 8.0以上版本 需要用这个驱动
            dsc.setDriverName("com.mysql.jdbc.Driver");
            dsc.setUsername(this.dbUserName);
            dsc.setPassword(this.dbPassword);
            dsc.setUrl(this.dbUrl);
            mpg.setDataSource(dsc);
            StrategyConfig strategy = new StrategyConfig();
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setInclude(this.includeTable);
            strategy.setExclude(this.excludeTable);
            mpg.setStrategy(strategy);
            PackageConfig pc = new PackageConfig();
            pc.setParent("com.east.sword");
            pc.setModuleName(this.moduleName);
            mpg.setPackageInfo(pc);
            mpg.execute();
        }

    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        MpHelper mh = new MpHelper("D:\\src\\main\\java",
                "root",
                "root",
                "jdbc:mysql://127.0.0.1:3306/screen?useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai",
                "screen");
        mh.setIncludeTable(new String []{"sys_role","sys_menu","ftp_info","screen_ftp"});// 需要自动生成的table 列表
        mh.execute();
    }
}
