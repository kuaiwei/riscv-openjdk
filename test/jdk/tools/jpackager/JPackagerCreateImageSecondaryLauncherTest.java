/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

 /*
 * @test
 * @summary jpackager create image with secondary launcher test
 * @build JPackagerHelper
 * @modules jdk.jpackager
 * @run main/othervm -Xmx512m JPackagerCreateImageSecondaryLauncherTest
 */
public class JPackagerCreateImageSecondaryLauncherTest {
    private static final String app;
    private static final String app2;
    private static final String appOutput = "appOutput.txt";
    private static final String appOutputPath;
    private static final String MSG = "jpackager test application";
    private static final String [] CMD = {
        "create-image",
        "--input", "input",
        "--output", "output",
        "--name", "test",
        "--main-jar", "hello.jar",
        "--class", "Hello",
        "--files", "hello.jar",
        "--force",
        "--secondary-launcher", "sl.properties"};

    // Note: quotes in argument for secondary launcher is not support by test
    private static final String ARGUMENT1 = "argument 1";
    private static final String ARGUMENT2 = "argument 2";
    private static final String ARGUMENT3 = "argument 3";

    private static final List<String> arguments = new ArrayList<>();

    private static final String PARAM1 = "-Dparam1=Some Param 1";
    private static final String PARAM2 = "-Dparam2=Some Param 2";
    private static final String PARAM3 = "-Dparam3=Some Param 3";

    private static final List<String> vmArguments = new ArrayList<>();

    static {
        if (JPackagerHelper.isWindows()) {
            app = "output" + File.separator + "test"
                    + File.separator + "test.exe";
            app2 = "output" + File.separator + "test"
                    + File.separator + "test2.exe";
            appOutputPath = "output" + File.separator + "test"
                    + File.separator + "app";
        } else if (JPackagerHelper.isOSX()) {
            app = "output" + File.separator + "test.app"
                    + File.separator + "Contents"
                    + File.separator + "MacOS" + File.separator + "test";
            app2 = "output" + File.separator + "test.app"
                    + File.separator + "Contents"
                    + File.separator + "MacOS" + File.separator + "test2";
            appOutputPath = "output" + File.separator + "test.app"
                    + File.separator + "Contents" + File.separator + "Java";
        } else {
            app = "output" + File.separator + "test" + File.separator + "test";
            app2 = "output" + File.separator + "test"
                    + File.separator + "test2";
            appOutputPath = "output" + File.separator + "test"
                    + File.separator + "app";
        }
    }

    private static void validateResult(List<String> args, List<String> vmArgs)
            throws Exception {
        File outfile = new File(appOutputPath + File.separator + appOutput);
        if (!outfile.exists()) {
            throw new AssertionError(appOutput + " was not created");
        }

        String output = Files.readString(outfile.toPath());
        String[] result = output.split("\n");

        if (result.length != (args.size() + vmArgs.size() + 2)) {
            throw new AssertionError("Unexpected number of lines: "
                    + result.length);
        }

        if (!result[0].trim().equals("jpackager test application")) {
            throw new AssertionError("Unexpected result[0]: " + result[0]);
        }

        if (!result[1].trim().equals("args.length: " + args.size())) {
            throw new AssertionError("Unexpected result[1]: " + result[1]);
        }

        int index = 2;
        for (String arg : args) {
            if (!result[index].trim().equals(arg)) {
                throw new AssertionError("Unexpected result[" + index + "]: "
                    + result[index]);
            }
            index++;
        }

        for (String vmArg : vmArgs) {
            if (!result[index].trim().equals(vmArg)) {
                throw new AssertionError("Unexpected result[" + index + "]: "
                    + result[index]);
            }
            index++;
        }
    }

    private static void validate() throws Exception {
        int retVal = JPackagerHelper.execute(null, app);
        if (retVal != 0) {
            throw new AssertionError("Test application exited with error: "
                    + retVal);
        }
        validateResult(new ArrayList<>(), new ArrayList<>());

        retVal = JPackagerHelper.execute(null, app2);
        if (retVal != 0) {
            throw new AssertionError("Test application exited with error: "
                    + retVal);
        }
        validateResult(arguments, vmArguments);
    }

    private static void testCreateImage() throws Exception {
        JPackagerHelper.executeCLI(true, CMD);
        validate();
    }

    private static void testCreateImageToolProvider() throws Exception {
        JPackagerHelper.executeToolProvider(true, CMD);
        validate();
    }

    private static void createSLProperties() throws Exception {
        arguments.add(ARGUMENT1);
        arguments.add(ARGUMENT2);
        arguments.add(ARGUMENT3);

        String argumentsMap =
                JPackagerHelper.listToArgumentsMap(arguments, true);

        vmArguments.add(PARAM1);
        vmArguments.add(PARAM2);
        vmArguments.add(PARAM3);

        String vmArgumentsMap =
                JPackagerHelper.listToArgumentsMap(vmArguments, true);

        try (PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter("sl.properties")))) {
            out.println("name=test2");
            out.println("arguments=" + argumentsMap);
            out.println("jvm-args=" + vmArgumentsMap);
        }
    }

    public static void main(String[] args) throws Exception {
        JPackagerHelper.createHelloJar();
        createSLProperties();
        testCreateImage();
        testCreateImageToolProvider();
    }

}
