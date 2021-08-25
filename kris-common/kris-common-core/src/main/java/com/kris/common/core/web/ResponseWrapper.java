package com.kris.common.core.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * <p>
 * 可用于重写返回值 基于HttpServletResponse
 * </p>
 *
 * @author livk
 * @date 2021/8/21
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
  private final ByteArrayOutputStream buffer;
  private final ServletOutputStream out;
  private final PrintWriter writer;

  public ResponseWrapper(HttpServletResponse response) throws IOException {
    super(response);
    buffer = new ByteArrayOutputStream();
    out = new WrapperOutputStream(buffer);
    writer = new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    return out;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    return writer;
  }

  @Override
  public void flushBuffer() throws IOException {
    if (out != null) {
      out.flush();
    }
    if (writer != null) {
      writer.flush();
    }
  }

  @Override
  public void reset() {
    buffer.reset();
  }

  public byte[] getResponseData() throws IOException {
    this.flushBuffer();
    return buffer.toByteArray();
  }

  private static class WrapperOutputStream extends ServletOutputStream {

    private ByteArrayOutputStream stream = null;

    public WrapperOutputStream(ByteArrayOutputStream stream) {
      this.stream = stream;
    }

    @Override
    public void write(int b) throws IOException {
      stream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
      stream.write(b, 0, b.length);
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
//      log.info("异步listener");
    }

    @Override
    public boolean isReady() {
      return false;
    }
  }
}
