/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the LGPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at  http://www.gnu.org/licenses/lgpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jn.sqlhelper.langx.configuration.file.directoryfile;

import com.jn.langx.util.Preconditions;
import com.jn.langx.util.Strings;
import com.jn.langx.util.Throwables;
import com.jn.langx.util.function.Supplier;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.io.file.Files;
import com.jn.langx.util.io.file.filter.WriteableFileFilter;
import com.jn.sqlhelper.langx.configuration.Configuration;
import com.jn.sqlhelper.langx.configuration.ConfigurationSerializer;
import com.jn.sqlhelper.langx.configuration.ConfigurationWriter;
import com.jn.sqlhelper.langx.util.Preconditions2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class DirectoryBasedFileConfigurationWriter<T extends Configuration> implements ConfigurationWriter<T> {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryBasedFileConfigurationWriter.class);
    private String directory;
    private ConfigurationSerializer<T, String> configurationSerializer;

    private Supplier<String, String> filenameSupplier;
    private Charset encoding = Charsets.getCharset("utf-8");

    @Override
    public void write(T configuration) {
        Preconditions.checkNotNull(configuration);
        String configString = configurationSerializer.serialize(configuration);
        if (Strings.isEmpty(configString)) {
            return;
        }
        String filepath = directory + File.separator + filenameSupplier.get(configuration.getId());
        BufferedOutputStream outputStream = null;
        try {
            if (Files.makeFile(filepath)) {
                outputStream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
                IOs.write(configString, outputStream, encoding);
            } else {
                logger.warn("write configuration to file fail, file: {}, configuration: {}", filepath, configuration);
            }
        } catch (IOException ex) {
            throw Throwables.wrapAsRuntimeException(ex);
        } finally {
            IOs.close(outputStream);
        }

    }

    public void setEncoding(String encoding) {
        this.encoding = Charsets.getCharset(encoding);
    }

    public void setDirectory(String directory) {
        Preconditions.checkNotNull(directory);
        Preconditions2.test(new WriteableFileFilter(), new File(directory));
        this.directory = directory;
    }

    public void setConfigurationSerializer(ConfigurationSerializer<T, String> configurationSerializer) {
        this.configurationSerializer = configurationSerializer;
    }

    public void setFilenameSupplier(Supplier<String, String> filenameSupplier) {
        this.filenameSupplier = filenameSupplier;
    }


}