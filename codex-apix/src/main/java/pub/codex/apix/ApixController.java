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

    /**
     * 生成并获取API列表接口
     * @return
     */
    @GetMapping("codex/apix/doc")
    @ResponseBody
    public DocDocument api() {
        return new DocDocument(documentationCache.getDocumentationLookup());
    }

}
