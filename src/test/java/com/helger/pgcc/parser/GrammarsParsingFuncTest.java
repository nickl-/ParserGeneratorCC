package com.helger.pgcc.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.helger.commons.io.file.FileSystemIterator;
import com.helger.commons.io.file.FileSystemRecursiveIterator;
import com.helger.commons.io.file.FilenameHelper;
import com.helger.commons.io.file.IFileFilter;
import com.helger.commons.state.ESuccess;
import com.helger.pgcc.jjtree.JJTree;

public final class GrammarsParsingFuncTest
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (GrammarsParsingFuncTest.class);

  private static void _parseCreatedJavaFiles (@Nonnull final File fGrammarDest,
                                              @Nonnull final Charset aCharset) throws FileNotFoundException
  {
    // Parse all created Java files
    for (final File fJava : new FileSystemIterator (fGrammarDest).withFilter (IFileFilter.filenameEndsWith (".java")))
    {
      s_aLogger.info ("  Java Parsing " + fJava.getName () + " in " + aCharset.name ());
      final CompilationUnit aCU = JavaParser.parse (fJava, aCharset);
      assertNotNull (aCU);
    }
  }

  @Test
  public void testParseDemoGrammars () throws Exception
  {
    final File fDest = new File ("target/grammars");
    fDest.mkdirs ();

    for (final File f : new FileSystemIterator (new File ("grammars")).withFilter (IFileFilter.filenameEndsWith (".jj")))
    {
      s_aLogger.info ("Parsing " + f.getName ());

      final File fGrammarDest = new File (fDest, FilenameHelper.getBaseName (f));
      fGrammarDest.mkdirs ();

      final ESuccess eSuccess = Main.mainProgram (new String [] { "-OUTPUT_DIRECTORY=" +
                                                                  fGrammarDest.getAbsolutePath (),
                                                                  "-JDK_VERSION=1.8",
                                                                  f.getAbsolutePath () });
      assertTrue (eSuccess.isSuccess ());

      _parseCreatedJavaFiles (fGrammarDest, StandardCharsets.UTF_8);
    }
  }

  @Test
  public void testParseDemoGrammarsJDK15 () throws Exception
  {
    final File fDest = new File ("target/grammars");
    fDest.mkdirs ();

    for (final File f : new FileSystemIterator (new File ("grammars")).withFilter (IFileFilter.filenameEndsWith (".jj")))
    {
      s_aLogger.info ("Parsing " + f.getName ());

      final File fGrammarDest = new File (fDest, FilenameHelper.getBaseName (f));
      fGrammarDest.mkdirs ();

      final ESuccess eSuccess = Main.mainProgram (new String [] { "-OUTPUT_DIRECTORY=" +
                                                                  fGrammarDest.getAbsolutePath (),
                                                                  "-JDK_VERSION=1.5",
                                                                  f.getAbsolutePath () });
      assertTrue (eSuccess.isSuccess ());

      _parseCreatedJavaFiles (fGrammarDest, StandardCharsets.UTF_8);
    }
  }

  @Test
  public void testParseDemoGrammarsModern () throws Exception
  {
    final File fDest = new File ("target/grammars");
    fDest.mkdirs ();

    for (final File f : new FileSystemIterator (new File ("grammars")).withFilter (IFileFilter.filenameEndsWith (".jj")))
    {
      s_aLogger.info ("Parsing " + f.getName ());

      final File fGrammarDest = new File (fDest, FilenameHelper.getBaseName (f));
      fGrammarDest.mkdirs ();

      final ESuccess eSuccess = Main.mainProgram (new String [] { "-OUTPUT_DIRECTORY=" +
                                                                  fGrammarDest.getAbsolutePath (),
                                                                  "-JDK_VERSION=1.8",
                                                                  "-JAVA_TEMPLATE_TYPE=modern",
                                                                  f.getAbsolutePath () });
      assertTrue (eSuccess.isSuccess ());

      _parseCreatedJavaFiles (fGrammarDest, StandardCharsets.UTF_8);
    }
  }

  @Test
  public void testParseDemoGrammarsJJT () throws Exception
  {
    final File fDest = new File ("target/grammars");
    fDest.mkdirs ();

    for (final File f : new FileSystemIterator (new File ("grammars")).withFilter (IFileFilter.filenameEndsWith (".jjt")))
    {
      s_aLogger.info ("Parsing " + f.getName ());

      final File fGrammarDest = new File (fDest, FilenameHelper.getBaseName (f));
      fGrammarDest.mkdirs ();

      final ESuccess eSuccess = new JJTree ().main (new String [] { "-OUTPUT_DIRECTORY=" +
                                                                    fGrammarDest.getAbsolutePath (),
                                                                    "-JDK_VERSION=1.8",
                                                                    f.getAbsolutePath () });
      assertTrue (eSuccess.isSuccess ());

      _parseCreatedJavaFiles (fGrammarDest, StandardCharsets.UTF_8);
    }
  }

  @Test
  public void testParseExamples () throws Exception
  {
    final File fDest = new File ("target/examples");
    fDest.mkdirs ();

    for (final File f : new FileSystemRecursiveIterator (new File ("examples")).withFilter (IFileFilter.filenameEndsWith (".jj")))
    {
      final String sBaseName = FilenameHelper.getBaseName (f);
      s_aLogger.info ("Parsing " + f.getParentFile ().getName () + "/" + f.getName ());

      final File fGrammarDest = new File (fDest, sBaseName);
      fGrammarDest.mkdirs ();

      final ESuccess eSuccess = Main.mainProgram (new String [] { "-OUTPUT_DIRECTORY=" +
                                                                  fGrammarDest.getAbsolutePath (),
                                                                  "-JDK_VERSION=1.8",
                                                                  f.getAbsolutePath () });
      assertTrue (eSuccess.isSuccess ());

      _parseCreatedJavaFiles (fGrammarDest, StandardCharsets.UTF_8);
    }
  }
}
