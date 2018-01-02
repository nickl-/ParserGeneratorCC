/* Copyright (c) 2006, Sun Microsystems, Inc.
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

import java.util.HashMap;
import java.util.Map;

import com.helger.pgcc.parser.CodeProductionCpp;
import com.helger.pgcc.parser.Expansion;
import com.helger.pgcc.parser.CodeProductionJava;
import com.helger.pgcc.parser.NonTerminal;
import com.helger.pgcc.parser.NormalProduction;
import com.helger.pgcc.parser.RegularExpression;
import com.helger.pgcc.parser.TokenProduction;

/**
 * Output BNF in HTML 3.2 format.
 */
public class HTMLGenerator extends TextGenerator
{
  private final Map <String, String> id_map = new HashMap <> ();
  private int id = 1;

  public HTMLGenerator ()
  {}

  protected String get_id (final String nt)
  {
    return id_map.computeIfAbsent (nt, k -> "prod" + id++);
  }

  private void println (final String s)
  {
    print (s + "\n");
  }

  @Override
  public void text (final String s)
  {
    final StringBuilder ret = new StringBuilder (s.length () * 2);
    for (final char c : s.toCharArray ())
      switch (c)
      {
        case '<':
          ret.append ("&lt;");
          break;
        case '>':
          ret.append ("&gt;");
          break;
        case '&':
          ret.append ("&amp;");
          break;
        default:
          ret.append (c);
          break;
      }

    print (ret.toString ());
  }

  @Override
  public void print (final String s)
  {
    ostr.print (s);
  }

  @Override
  public void documentStart ()
  {
    ostr = create_output_stream ();
    println ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
    println ("<HTML>");
    println ("<HEAD>");
    if (!"".equals (JJDocOptions.getCSS ()))
    {
      println ("<LINK REL=\"stylesheet\" type=\"text/css\" href=\"" + JJDocOptions.getCSS () + "\"/>");
    }
    if (JJDocGlobals.s_input_file != null)
    {
      println ("<TITLE>BNF for " + JJDocGlobals.s_input_file + "</TITLE>");
    }
    else
    {
      println ("<TITLE>A BNF grammar by JJDoc</TITLE>");
    }
    println ("</HEAD>");
    println ("<BODY>");
    println ("<H1 ALIGN=CENTER>BNF for " + JJDocGlobals.s_input_file + "</H1>");
  }

  @Override
  public void documentEnd ()
  {
    println ("</BODY>");
    println ("</HTML>");
    ostr.close ();
  }

  /**
   * Prints out comments, used for tokens and non-terminals. {@inheritDoc}
   *
   * @see org.javacc.jjdoc.TextGenerator#specialTokens(java.lang.String)
   */
  @Override
  public void specialTokens (final String s)
  {
    println (" <!-- Special token -->");
    println (" <TR>");
    println ("  <TD>");
    println ("<PRE>");
    print (s);
    println ("</PRE>");
    println ("  </TD>");
    println (" </TR>");
  }

  @Override
  public void handleTokenProduction (final TokenProduction tp)
  {
    println (" <!-- Token -->");
    println (" <TR>");
    println ("  <TD>");
    println ("   <PRE>");
    final String text = JJDoc.getStandardTokenProductionText (tp);
    text (text);
    println ("   </PRE>");
    println ("  </TD>");
    println (" </TR>");
  }

  @Override
  public void nonterminalsStart ()
  {
    println ("<H2 ALIGN=CENTER>NON-TERMINALS</H2>");
    if (JJDocOptions.isOneTable ())
    {
      println ("<TABLE>");
    }
  }

  @Override
  public void nonterminalsEnd ()
  {
    if (JJDocOptions.isOneTable ())
    {
      println ("</TABLE>");
    }
  }

  @Override
  public void tokensStart ()
  {
    println ("<H2 ALIGN=CENTER>TOKENS</H2>");
    println ("<TABLE>");
  }

  @Override
  public void tokensEnd ()
  {
    println ("</TABLE>");
  }

  @Override
  public void javacode (final CodeProductionJava jp)
  {
    productionStart (jp);
    println ("<I>java code</I></TD></TR>");
    productionEnd (jp);
  }

  @Override
  public void cppcode (final CodeProductionCpp cp)
  {
    productionStart (cp);
    println ("<I>cpp code</I></TD></TR>");
    productionEnd (cp);
  }

  @Override
  public void productionStart (final NormalProduction np)
  {
    if (!JJDocOptions.isOneTable ())
    {
      println ("");
      println ("<TABLE ALIGN=CENTER>");
      println ("<CAPTION><STRONG>" + np.getLhs () + "</STRONG></CAPTION>");
    }
    println ("<TR>");
    println ("<TD ALIGN=RIGHT VALIGN=BASELINE><A NAME=\"" + get_id (np.getLhs ()) + "\">" + np.getLhs () + "</A></TD>");
    println ("<TD ALIGN=CENTER VALIGN=BASELINE>::=</TD>");
    print ("<TD ALIGN=LEFT VALIGN=BASELINE>");
  }

  @Override
  public void productionEnd (final NormalProduction np)
  {
    if (!JJDocOptions.isOneTable ())
    {
      println ("</TABLE>");
      println ("<HR>");
    }
  }

  @Override
  public void expansionStart (final Expansion e, final boolean first)
  {
    if (!first)
    {
      println ("<TR>");
      println ("<TD ALIGN=RIGHT VALIGN=BASELINE></TD>");
      println ("<TD ALIGN=CENTER VALIGN=BASELINE>|</TD>");
      print ("<TD ALIGN=LEFT VALIGN=BASELINE>");
    }
  }

  @Override
  public void expansionEnd (final Expansion e, final boolean first)
  {
    println ("</TD>");
    println ("</TR>");
  }

  @Override
  public void nonTerminalStart (final NonTerminal nt)
  {
    print ("<A HREF=\"#" + get_id (nt.getName ()) + "\">");
  }

  @Override
  public void nonTerminalEnd (final NonTerminal nt)
  {
    print ("</A>");
  }

  @Override
  public void reStart (final RegularExpression r)
  {}

  @Override
  public void reEnd (final RegularExpression r)
  {}
}