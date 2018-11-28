package pub.codex.apix;

import org.springframework.stereotype.Component;
import pub.codex.apix.schema.Documentation;

@Component
public class DocumentationCache {

    private Documentation documentation;

    public void addDocumentation(Documentation documentation) {
        this.documentation = documentation;
    }

    public Documentation getDocumentationLookup() {
        return this.documentation;
    }


}
