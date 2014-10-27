package uk.ac.gla.teamL;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

/**
 * User: nishad
 * Date: 27/10/14
 * Time: 15:27
 */
public class EBNFFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(EBNFFileType.INSTANCE, EBNFFileType.INSTANCE.getName());
    }
}
