package com.xd.kobepcommon.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xd.kobepcommon.constants.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动生成代码
 */
@Component
public class CodeGenerator {

    @Autowired
    private Environment env;


    /**
     *
     * @param projName 项目名
     * @param moduleName 模块名
     * @param parentPath 包名
     * @param tableNames 表名
     * @throws Exception
     */
    public  void generator(String projName,String moduleName,String parentPath,String[] tableNames) throws Exception{

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String  projectPath = System.getProperty("user.dir");

        if (StringUtils.isNotBlank(projName)){

           String panentProNmae = projectPath.substring(projectPath.lastIndexOf("\\")+1,projectPath.length());

            projectPath = projectPath.replace(panentProNmae,panentProNmae + "\\" + projName);

        }
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(CommonConstants.CODE_AUTHOR);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        //开启二级缓存
        //gc.setEnableCache(true);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(env.getProperty("spring.datasource.url"));
        // dsc.setSchemaName("public");
        dsc.setDriverName(env.getProperty("spring.datasource.driverClassName"));
        dsc.setUsername(env.getProperty("spring.datasource.username"));
        dsc.setPassword(env.getProperty("spring.datasource.password"));
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();

        if (StringUtils.isNotBlank(moduleName)){
            pc.setModuleName(moduleName);
        }

        pc.setParent(parentPath);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        // 自定义配置会被优先输出
        final String xmlProjectPath = projectPath;

        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                if (StringUtils.isNotBlank(pc.getModuleName())){

                    return xmlProjectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                            + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
                return xmlProjectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //     strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        // 公共父类
        //      strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");

        strategy.setInclude(tableNames);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setEntityTableFieldAnnotationEnable(true);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();


    }
}
