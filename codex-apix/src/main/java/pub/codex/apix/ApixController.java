package pub.codex.apix;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.codec.CodecException;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pub.codex.apix.annotations.Api;
import pub.codex.apix.doc.DocDocument;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Api("演示API管理")
@RestController
public class ApixController {

    @Autowired
    private DocumentationCache documentationCache;

    @GetMapping("apix/doc")
    @ResponseBody
    public DocDocument api() {

        return new DocDocument(documentationCache.getDocumentationLookup());
    }


    /**
     * todo 这个方法报错需改正
     */
    @GetMapping("apix/getDoc")
    @ResponseBody
    public void getDoc() {
        DocDocument docDocument = new DocDocument(documentationCache.getDocumentationLookup());
        Configuration cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:template")); //模板文件夹路径

            Template template = cfg.getTemplate("doc.ftl");  //模板文件
            cfg.setDefaultEncoding("UTF-8");
            Map root = new HashMap();
            root.put("controller", docDocument.getController());

            //文档默认输出到用户桌面，文档名称为API接口文档.doc
            Writer out1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()+"\\API接口文档.doc"), "UTF-8"));

            template.process(root, out1);
            out1.close();
        } catch (IOException e) {
            throw new CodecException("IOException:"+e.getMessage());
        } catch (TemplateException e) {
            throw new CodecException("TemplateException:"+e.getMessage());
        }
    }
}
