/**
 * Copyright 2017-2018 Philip Helger, pgcc@helger.com
 *
 * Copyright 2011 Google Inc. All Rights Reserved.
 * Author: sreeni@google.com (Sreeni Viswanadha)
 *
 * Copyright (c) 2006, Sun Microsystems, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Sun Microsystems, Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.helger.pgcc.jjdoc;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.helger.pgcc.parser.AbstractExpRegularExpression;
import com.helger.pgcc.parser.CodeProductionCpp;
import com.helger.pgcc.parser.CodeProductionJava;
import com.helger.pgcc.parser.ExpNonTerminal;
import com.helger.pgcc.parser.ExpRCharacterList;
import com.helger.pgcc.parser.ExpRJustName;
import com.helger.pgcc.parser.Expansion;
import com.helger.pgcc.parser.NormalProduction;
import com.helger.pgcc.parser.TokenProduction;

public class BNFGenerator implements IDocGenerator
{
  private final Map <String, String> m_id_map = new HashMap <> ();
  private int m_id = 1;
  protected Writer m_aPW;
  private boolean m_bPrinting = true;

  protected String get_id (final String nt)
  {
    return m_id_map.computeIfAbsent (nt, k -> "prod" + m_id++);
  }

  protected static Writer create_output_stream ()
  {
    return TextGenerator.createPrintWriter (".bnf");
  }

  public void text (final String s) throws IOException
  {
    if (m_bPrinting && !(s.length () == 1 && (s.charAt (0) == '\n' || s.charAt (0) == '\r')))
    {
      print (s);
    }
  }

  public void print (final String s) throws IOException
  {
    m_aPW.write (s);
  }

  public void documentStart ()
  {
    m_aPW = create_output_stream ();
  }

  public void documentEnd () throws IOException
  {
    m_aPW.close ();
  }

  public void specialTokens (final String s)
  {}

  // public void tokenStart(TokenProduction tp) {
  // printing = false;
  // }
  // public void tokenEnd(TokenProduction tp) {
  // printing = true;
  // }
  public void nonterminalsStart ()
  {}

  public void nonterminalsEnd ()
  {}

  @Override
  public void tokensStart ()
  {}

  @Override
  public void tokensEnd ()
  {}

  public void javacode (final CodeProductionJava jp)
  {}

  public void cppcode (final CodeProductionCpp cp)
  {}

  public void expansionEnd (final Expansion e, final boolean first)
  {}

  public void nonTerminalStart (final ExpNonTerminal nt)
  {}

  public void nonTerminalEnd (final ExpNonTerminal nt)
  {}

  public void productionStart (final NormalProduction np) throws IOException
  {
    print ("\n");
    print (np.getLhs () + " ::= ");
  }

  public void productionEnd (final NormalProduction np) throws IOException
  {
    print ("\n");
  }

  public void expansionStart (final Expansion e, final boolean first) throws IOException
  {
    if (!first)
    {
      print (" | ");
    }
  }

  public void reStart (final AbstractExpRegularExpression r)
  {
    if (r.getClass ().equals (ExpRJustName.class) || r.getClass ().equals (ExpRCharacterList.class))
    {
      m_bPrinting = false;
    }
  }

  public void reEnd (final AbstractExpRegularExpression r)
  {
    m_bPrinting = true;
  }

  @Override
  public void handleTokenProduction (final TokenProduction tp) throws IOException
  {
    m_bPrinting = false;
    final String text = JJDoc.getStandardTokenProductionText (tp);
    text (text);
    m_bPrinting = true;
  }
}
