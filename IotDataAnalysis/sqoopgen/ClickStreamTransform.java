// ORM class for table 'ClickStreamTransform'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri Jul 07 16:13:16 CST 2017
// For connector: org.apache.sqoop.manager.MySQLManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import com.cloudera.sqoop.lib.JdbcWritableBridge;
import com.cloudera.sqoop.lib.DelimiterSet;
import com.cloudera.sqoop.lib.FieldFormatter;
import com.cloudera.sqoop.lib.RecordParser;
import com.cloudera.sqoop.lib.BooleanParser;
import com.cloudera.sqoop.lib.BlobRef;
import com.cloudera.sqoop.lib.ClobRef;
import com.cloudera.sqoop.lib.LargeObjectLoader;
import com.cloudera.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ClickStreamTransform extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  protected ResultSet __cur_result_set;
  private String process;
  public String get_process() {
    return process;
  }
  public void set_process(String process) {
    this.process = process;
  }
  public ClickStreamTransform with_process(String process) {
    this.process = process;
    return this;
  }
  private Integer counts;
  public Integer get_counts() {
    return counts;
  }
  public void set_counts(Integer counts) {
    this.counts = counts;
  }
  public ClickStreamTransform with_counts(Integer counts) {
    this.counts = counts;
    return this;
  }
  private Integer countu;
  public Integer get_countu() {
    return countu;
  }
  public void set_countu(Integer countu) {
    this.countu = countu;
  }
  public ClickStreamTransform with_countu(Integer countu) {
    this.countu = countu;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClickStreamTransform)) {
      return false;
    }
    ClickStreamTransform that = (ClickStreamTransform) o;
    boolean equal = true;
    equal = equal && (this.process == null ? that.process == null : this.process.equals(that.process));
    equal = equal && (this.counts == null ? that.counts == null : this.counts.equals(that.counts));
    equal = equal && (this.countu == null ? that.countu == null : this.countu.equals(that.countu));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClickStreamTransform)) {
      return false;
    }
    ClickStreamTransform that = (ClickStreamTransform) o;
    boolean equal = true;
    equal = equal && (this.process == null ? that.process == null : this.process.equals(that.process));
    equal = equal && (this.counts == null ? that.counts == null : this.counts.equals(that.counts));
    equal = equal && (this.countu == null ? that.countu == null : this.countu.equals(that.countu));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.process = JdbcWritableBridge.readString(1, __dbResults);
    this.counts = JdbcWritableBridge.readInteger(2, __dbResults);
    this.countu = JdbcWritableBridge.readInteger(3, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.process = JdbcWritableBridge.readString(1, __dbResults);
    this.counts = JdbcWritableBridge.readInteger(2, __dbResults);
    this.countu = JdbcWritableBridge.readInteger(3, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(process, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeInteger(counts, 2 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(countu, 3 + __off, 4, __dbStmt);
    return 3;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(process, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeInteger(counts, 2 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(countu, 3 + __off, 4, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.process = null;
    } else {
    this.process = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.counts = null;
    } else {
    this.counts = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.countu = null;
    } else {
    this.countu = Integer.valueOf(__dataIn.readInt());
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.process) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, process);
    }
    if (null == this.counts) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.counts);
    }
    if (null == this.countu) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.countu);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.process) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, process);
    }
    if (null == this.counts) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.counts);
    }
    if (null == this.countu) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.countu);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 9, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(process==null?"null":process, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(counts==null?"null":"" + counts, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(countu==null?"null":"" + countu, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(process==null?"null":process, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(counts==null?"null":"" + counts, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(countu==null?"null":"" + countu, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 9, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("null")) { this.process = null; } else {
      this.process = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.counts = null; } else {
      this.counts = Integer.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.countu = null; } else {
      this.countu = Integer.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    __cur_str = __it.next();
    if (__cur_str.equals("null")) { this.process = null; } else {
      this.process = __cur_str;
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.counts = null; } else {
      this.counts = Integer.valueOf(__cur_str);
    }

    __cur_str = __it.next();
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.countu = null; } else {
      this.countu = Integer.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    ClickStreamTransform o = (ClickStreamTransform) super.clone();
    return o;
  }

  public void clone0(ClickStreamTransform o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new TreeMap<String, Object>();
    __sqoop$field_map.put("process", this.process);
    __sqoop$field_map.put("counts", this.counts);
    __sqoop$field_map.put("countu", this.countu);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("process", this.process);
    __sqoop$field_map.put("counts", this.counts);
    __sqoop$field_map.put("countu", this.countu);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if ("process".equals(__fieldName)) {
      this.process = (String) __fieldVal;
    }
    else    if ("counts".equals(__fieldName)) {
      this.counts = (Integer) __fieldVal;
    }
    else    if ("countu".equals(__fieldName)) {
      this.countu = (Integer) __fieldVal;
    }
    else {
      throw new RuntimeException("No such field: " + __fieldName);
    }
  }
  public boolean setField0(String __fieldName, Object __fieldVal) {
    if ("process".equals(__fieldName)) {
      this.process = (String) __fieldVal;
      return true;
    }
    else    if ("counts".equals(__fieldName)) {
      this.counts = (Integer) __fieldVal;
      return true;
    }
    else    if ("countu".equals(__fieldName)) {
      this.countu = (Integer) __fieldVal;
      return true;
    }
    else {
      return false;    }
  }
}
