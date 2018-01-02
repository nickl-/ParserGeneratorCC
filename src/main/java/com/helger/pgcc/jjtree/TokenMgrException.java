package com.helger.pgcc.jjtree;

/** Token Manager Error. */
public class TokenMgrException extends RuntimeException
{
  /*
   * Ordinals for various reasons why an Error of this type can be thrown.
   */
  /**
   * Lexical error occurred.
   */
  public static final int LEXICAL_ERROR = 0;

  /**
   * An attempt was made to create a second instance of a static token manager.
   */
  public static final int STATIC_LEXER_ERROR = 1;

  /**
   * Tried to change to an invalid lexical state.
   */
  public static final int INVALID_LEXICAL_STATE = 2;

  /**
   * Detected (and bailed out of) an infinite loop in the token manager.
   */
  public static final int LOOP_DETECTED = 3;

  /**
   * Indicates the reason why the exception is thrown. It will have one of the
   * above 4 values.
   */
  private final int errorCode;

  /**
   * Replaces unprintable characters by their escaped (or unicode escaped)
   * equivalents in the given string
   */
  protected static final String addEscapes (final String str)
  {
    final StringBuilder retval = new StringBuilder (str.length () * 2);
    for (final char ch : str.toCharArray ())
    {
      switch (ch)
      {
        case 0:
          continue;
        case '\b':
          retval.append ("\\b");
          continue;
        case '\t':
          retval.append ("\\t");
          continue;
        case '\n':
          retval.append ("\\n");
          continue;
        case '\f':
          retval.append ("\\f");
          continue;
        case '\r':
          retval.append ("\\r");
          continue;
        case '\"':
          retval.append ("\\\"");
          continue;
        case '\'':
          retval.append ("\\\'");
          continue;
        case '\\':
          retval.append ("\\\\");
          continue;
        default:
          if (ch < 0x20 || ch > 0x7e)
          {
            final String s = "0000" + Integer.toString (ch, 16);
            retval.append ("\\u").append (s.substring (s.length () - 4, s.length ()));
          }
          else
          {
            retval.append (ch);
          }
          continue;
      }
    }
    return retval.toString ();
  }

  /**
   * Returns a detailed message for the Error when it is thrown by the token
   * manager to indicate a lexical error. Parameters : bEOFSeen : indicates if
   * EOF caused the lexical error curLexState : lexical state in which this
   * error occurred errorLine : line number when the error occurred errorColumn
   * : column number when the error occurred errorAfter : prefix that was seen
   * before this error occurred curchar : the offending character Note: You can
   * customize the lexical error message by modifying this method.
   *
   * @param bEOFSeen
   * @param lexState
   * @param errorLine
   * @param errorColumn
   * @param errorAfter
   * @param curChar
   * @return
   */
  protected static String buildLexicalErrorString (final boolean bEOFSeen,
                                                   final int lexState,
                                                   final int errorLine,
                                                   final int errorColumn,
                                                   final String errorAfter,
                                                   final char curChar)
  {
    return "Lexical error at line " +
           errorLine +
           ", column " +
           errorColumn +
           ".  Encountered: " +
           (bEOFSeen ? "<EOF> "
                     : ("\"" + addEscapes (Character.toString (curChar)) + "\"") + " (" + (int) curChar + "), ") +
           "after : \"" +
           addEscapes (errorAfter) +
           "\"";
  }

  /** Constructor with message and reason. */
  public TokenMgrException (final String message, final int reason)
  {
    super (message);
    errorCode = reason;
  }

  /** Full Constructor. */
  public TokenMgrException (final boolean bEOFSeen,
                            final int lexState,
                            final int errorLine,
                            final int errorColumn,
                            final String errorAfter,
                            final char curChar,
                            final int reason)
  {
    this (buildLexicalErrorString (bEOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
  }

  /**
   * You can also modify the body of this method to customize your error
   * messages. For example, cases like LOOP_DETECTED and INVALID_LEXICAL_STATE
   * are not of end-users concern, so you can return something like : "Internal
   * Error : Please file a bug report .... " from this method for such cases in
   * the release version of your parser.
   */
  @Override
  public String getMessage ()
  {
    return super.getMessage ();
  }
}