/*
 * Copyright (c) 2024, Alibaba Cloud;
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aliyun.dataworks.migrationx.transformer.core.sqoop;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tool that performs HDFS exports to databases.
 */
public class ExportTool extends BaseSqoopTool {

    protected String[] extraArguments;
    public static final Logger LOG = LoggerFactory.getLogger(ExportTool.class.getName());

    public ExportTool() {
        super("export");
    }

    /**
     * Construct the set of options that control exports.
     *
     * @return the RelatedOptions that can be used to parse the export arguments.
     */
    protected RelatedOptions getExportOptions() {
        RelatedOptions exportOpts = new RelatedOptions("Export control arguments");

        exportOpts.addOption(
            OptionBuilder.withDescription("Use direct export fast path").withLongOpt(DIRECT_ARG).create());
        exportOpts.addOption(OptionBuilder.withArgName("table-name").hasArg().withDescription("Table to populate")
            .withLongOpt(TABLE_ARG).create());
        exportOpts.addOption(OptionBuilder.withArgName("col,col,col...").hasArg()
            .withDescription("Columns to export to table").withLongOpt(COLUMNS_ARG).create());
        exportOpts.addOption(OptionBuilder.withArgName("name").hasArg()
            .withDescription("Set name for generated mapreduce job").withLongOpt(MAPREDUCE_JOB_NAME).create());
        exportOpts.addOption(
            OptionBuilder.withArgName("n").hasArg().withDescription("Use 'n' map tasks to export in parallel")
                .withLongOpt(NUM_MAPPERS_ARG).create(NUM_MAPPERS_SHORT_ARG));
        exportOpts.addOption(OptionBuilder.withArgName("dir").hasArg()
            .withDescription("HDFS source path for the export").withLongOpt(EXPORT_PATH_ARG).create());
        exportOpts.addOption(OptionBuilder.withArgName("key").hasArg()
            .withDescription("Update records by specified key column").withLongOpt(UPDATE_KEY_ARG).create());
        exportOpts.addOption(OptionBuilder.withArgName("table-name").hasArg()
            .withDescription("Intermediate staging table").withLongOpt(STAGING_TABLE_ARG).create());
        exportOpts
            .addOption(OptionBuilder.withDescription("Indicates that any data in " + "staging table can be deleted")
                .withLongOpt(CLEAR_STAGING_TABLE_ARG).create());
        exportOpts.addOption(
            OptionBuilder.withDescription("Indicates underlying statements " + "to be executed in batch mode")
                .withLongOpt(BATCH_ARG).create());
        exportOpts.addOption(OptionBuilder.withArgName("mode").hasArg()
            .withDescription("Specifies how updates are performed when "
                + "new rows are found with non-matching keys in database")
            .withLongOpt(UPDATE_MODE_ARG).create());
        exportOpts.addOption(OptionBuilder.hasArg()
            .withDescription("Populate the table using this stored " + "procedure (one call per row)")
            .withLongOpt(CALL_ARG).create());

        addValidationOpts(exportOpts);

        return exportOpts;
    }

    /**
     * Configure the command-line arguments we expect to receive
     */
    public void configureOptions(ToolOptions toolOptions) {

        toolOptions.addUniqueOptions(getCommonOptions());
        toolOptions.addUniqueOptions(getExportOptions());

        // Input parsing delimiters
        toolOptions.addUniqueOptions(getInputFormatOptions());

        // Used when sending data to a direct-mode export.
        toolOptions.addUniqueOptions(getOutputFormatOptions());

        // get common codegen opts.
        RelatedOptions codeGenOpts = getCodeGenOpts(false);

        // add export-specific codegen opts:
        codeGenOpts.addOption(OptionBuilder.withArgName("file").hasArg()
            .withDescription("Disable code generation; use specified jar").withLongOpt(JAR_FILE_NAME_ARG).create());

        toolOptions.addUniqueOptions(codeGenOpts);
        toolOptions.addUniqueOptions(getHCatalogOptions());
    }

    @Override
    /** {@inheritDoc} */
    public void printHelp(ToolOptions toolOptions) {
        super.printHelp(toolOptions);
        System.out.println("");
        System.out.println("At minimum, you must specify --connect, --export-dir, and --table");
    }

    public void applyOptions(CommandLine in, SqoopOptions out) throws InvalidOptionsException {

        try {
            applyCommonOptions(in, out);

            if (in.hasOption(DIRECT_ARG)) {
                out.setDirectMode(true);
            }

            if (in.hasOption(BATCH_ARG)) {
                out.setBatchMode(true);
            }

            if (in.hasOption(TABLE_ARG)) {
                out.setTableName(in.getOptionValue(TABLE_ARG));
            }

            if (in.hasOption(COLUMNS_ARG)) {
                String[] cols = in.getOptionValue(COLUMNS_ARG).split(",");
                for (int i = 0; i < cols.length; i++) {
                    cols[i] = cols[i].trim();
                }
                out.setColumns(cols);
            }

            if (in.hasOption(NUM_MAPPERS_ARG)) {
                out.setNumMappers(Integer.parseInt(in.getOptionValue(NUM_MAPPERS_ARG)));
            }

            if (in.hasOption(MAPREDUCE_JOB_NAME)) {
                out.setMapreduceJobName(in.getOptionValue(MAPREDUCE_JOB_NAME));
            }

            if (in.hasOption(EXPORT_PATH_ARG)) {
                out.setExportDir(in.getOptionValue(EXPORT_PATH_ARG));
            }

            if (in.hasOption(JAR_FILE_NAME_ARG)) {
                out.setExistingJarName(in.getOptionValue(JAR_FILE_NAME_ARG));
            }

            if (in.hasOption(UPDATE_KEY_ARG)) {
                out.setUpdateKeyCol(in.getOptionValue(UPDATE_KEY_ARG));
            }

            if (in.hasOption(STAGING_TABLE_ARG)) {
                out.setStagingTableName(in.getOptionValue(STAGING_TABLE_ARG));
            }

            if (in.hasOption(CLEAR_STAGING_TABLE_ARG)) {
                out.setClearStagingTable(true);
            }

            if (in.hasOption(CALL_ARG)) {
                out.setCall(in.getOptionValue(CALL_ARG));
            }

            applyValidationOptions(in, out);
            applyNewUpdateOptions(in, out);
            applyCodeGenOptions(in, out, false);
            applyHCatalogOptions(in, out);
        } catch (NumberFormatException nfe) {
            throw new InvalidOptionsException("Error: expected numeric argument.\n" + "Try --help for usage.");
        }
    }

    /**
     * Validate export-specific arguments.
     *
     * @param options the configured SqoopOptions to check
     */
    protected void validateExportOptions(SqoopOptions options) throws InvalidOptionsException {
        if (options.getTableName() == null && options.getCall() == null) {
            throw new InvalidOptionsException("Export requires a --table or a --call argument." + HELP_STR);
        } else if (options.getExportDir() == null && options.getHCatTableName() == null) {
            throw new InvalidOptionsException(
                "Export requires an --export-dir argument or " + "--hcatalog-table argument." + HELP_STR);
        } else if (options.getExistingJarName() != null && options.getClassName() == null) {
            throw new InvalidOptionsException(
                "Jar specified with --jar-file, but no " + "class specified with --class-name." + HELP_STR);
        } else if (options.getExistingJarName() != null && options.getUpdateKeyCol() != null) {
            // We need to regenerate the class with the output column order set
            // correctly for the update-based export. So we can't use a premade
            // class.
            throw new InvalidOptionsException(
                "Jar cannot be specified with " + "--jar-file when export is running in update mode.");
        } else if (options.getStagingTableName() != null && options.getUpdateKeyCol() != null) {
            // Staging table may not be used when export is running in update mode
            throw new InvalidOptionsException(
                "Staging table cannot be used when " + "export is running in update mode.");
        } else if (options.getStagingTableName() != null
            && options.getStagingTableName().equalsIgnoreCase(options.getTableName())) {
            // Name of staging table and destination table cannot be the same
            throw new InvalidOptionsException("Staging table cannot be the same as "
                + "the destination table. Name comparison used is case-insensitive.");
        } else if (options.doClearStagingTable() && options.getStagingTableName() == null) {
            // Option to clear staging table specified but not the staging table name
            throw new InvalidOptionsException(
                "Option to clear the staging table is " + "specified but the staging table name is not.");
        } else if (options.getCall() != null && options.getStagingTableName() != null) {
            // using a stored procedure to insert rows is incompatible with using
            // a staging table (as we don't know where the procedure will put the
            // data, or what transactions it'll perform)
            throw new InvalidOptionsException(
                "Option the use a staging table is " + "specified as well as a call option.");
        } else if (options.getCall() != null && options.getUpdateKeyCol() != null) {
            throw new InvalidOptionsException("Option to call a stored procedure" + "can't be used in update mode.");
        } else if (options.getCall() != null && options.getTableName() != null) {
            // we don't know if the stored procedure will insert rows into
            // a given table
            throw new InvalidOptionsException("Can't specify --call and --table.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateOptions(SqoopOptions options) throws InvalidOptionsException {

        // If extraArguments is full, check for '--' followed by args for
        // mysqldump or other commands we rely on.
        options.setExtraArgs(getSubcommandArgs(extraArguments));
        int dashPos = extraArguments.length;
        for (int i = 0; i < extraArguments.length; i++) {
            if (extraArguments[i].equals("--")) {
                dashPos = i;
                break;
            }
        }

        if (hasUnrecognizedArgs(extraArguments, 0, dashPos)) {
            throw new InvalidOptionsException(HELP_STR);
        }
    }

    private void applyNewUpdateOptions(CommandLine in, SqoopOptions out) {
        if (in.hasOption(UPDATE_MODE_ARG)) {
            String updateTypeStr = in.getOptionValue(UPDATE_MODE_ARG);
            if ("updateonly".equals(updateTypeStr)) {
                out.setUpdateMode(SqoopOptions.UpdateMode.UpdateOnly);
            } else if ("allowinsert".equals(updateTypeStr)) {
                out.setUpdateMode(SqoopOptions.UpdateMode.AllowInsert);
            } else {
                throw new InvalidOptionsException("Unknown new update mode: " + updateTypeStr
                    + ". Use 'updateonly' or 'allowinsert'." + HELP_STR);
            }
        }
    }

    @Override
    public int run(SqoopOptions options) {
        return 0;
    }
}
