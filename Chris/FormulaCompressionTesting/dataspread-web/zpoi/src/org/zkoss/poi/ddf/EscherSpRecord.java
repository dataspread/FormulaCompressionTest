/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.zkoss.poi.ddf;

import org.zkoss.poi.util.BitField;
import org.zkoss.poi.util.BitFieldFactory;
import org.zkoss.poi.util.HexDump;
import org.zkoss.poi.util.LittleEndian;

/**
 * Together the the EscherOptRecord this record defines some of the basic
 * properties of a shape.
 *
 * @author Glen Stampoultzis (glens at apache.org)
 */
public class EscherSpRecord
    extends EscherRecord
{
    public static final short RECORD_ID = (short) 0xF00A;
    public static final String RECORD_DESCRIPTION = "MsofbtSp";

    public static final int FLAG_GROUP = 0x0001;
    public static final int FLAG_CHILD = 0x0002;
    public static final int FLAG_PATRIARCH = 0x0004;
    public static final int FLAG_DELETED = 0x0008;
    public static final int FLAG_OLESHAPE = 0x0010;
    public static final int FLAG_HAVEMASTER = 0x0020;
    public static final int FLAG_FLIPHORIZ = 0x0040;
    public static final int FLAG_FLIPVERT = 0x0080;
    public static final int FLAG_CONNECTOR = 0x0100;
    public static final int FLAG_HAVEANCHOR = 0x0200;
    public static final int FLAG_BACKGROUND = 0x0400;
    public static final int FLAG_HASSHAPETYPE = 0x0800;

    private int field_1_shapeId;
    private int field_2_flags;

    public int fillFields(byte[] data, int offset, EscherRecordFactory recordFactory) {
        int bytesRemaining = readHeader( data, offset );
        int pos            = offset + 8;
        int size           = 0;
        field_1_shapeId    =  LittleEndian.getInt( data, pos + size );     size += 4;
        field_2_flags      =  LittleEndian.getInt( data, pos + size );     size += 4;
//        bytesRemaining -= size;
//        remainingData  =  new byte[bytesRemaining];
//        System.arraycopy( data, pos + size, remainingData, 0, bytesRemaining );
        return getRecordSize();
    }

    /**
     * This method serializes this escher record into a byte array.
     *
     * @param offset   The offset into <code>data</code> to start writing the record data to.
     * @param data     The byte array to serialize to.
     * @param listener A listener to retrieve start and end callbacks.  Use a <code>NullEscherSerailizationListener</code> to ignore these events.
     * @return The number of bytes written.
     *
     * @see NullEscherSerializationListener
     */
    public int serialize( int offset, byte[] data, EscherSerializationListener listener )
    {
        listener.beforeRecordSerialize( offset, getRecordId(), this );
        LittleEndian.putShort( data, offset, getOptions() );
        LittleEndian.putShort( data, offset + 2, getRecordId() );
        int remainingBytes = 8;
        LittleEndian.putInt( data, offset + 4, remainingBytes );
        LittleEndian.putInt( data, offset + 8, field_1_shapeId );
        LittleEndian.putInt( data, offset + 12, field_2_flags );
//        System.arraycopy( remainingData, 0, data, offset + 26, remainingData.length );
//        int pos = offset + 8 + 18 + remainingData.length;
        listener.afterRecordSerialize( offset + getRecordSize(), getRecordId(), getRecordSize(), this );
        return 8 + 8;
    }

    public int getRecordSize()
    {
        return 8 + 8;
    }

    public short getRecordId() {
        return RECORD_ID;
    }

    public String getRecordName() {
        return "Sp";
    }


    /**
     * @return  the string representing this shape.
     */
    public String toString()
    {
        String nl = System.getProperty("line.separator");

        return getClass().getName() + ":" + nl +
                "  RecordId: 0x" + HexDump.toHex(RECORD_ID) + nl +
                "  Version: 0x" + HexDump.toHex(getVersion()) + nl +
                "  ShapeType: 0x" + HexDump.toHex(getShapeType()) + nl +
                "  ShapeId: " + field_1_shapeId + nl +
                "  Flags: " + decodeFlags(field_2_flags) + " (0x" + HexDump.toHex(field_2_flags) + ")" + nl;

    }

    @Override
    public String toXml(String tab) {
        StringBuilder builder = new StringBuilder();
        builder.append(tab).append(formatXmlRecordHeader(getClass().getSimpleName(), HexDump.toHex(getRecordId()), HexDump.toHex(getVersion()), HexDump.toHex(getInstance())))
                .append(tab).append("\t").append("<ShapeType>0x").append(HexDump.toHex(getShapeType())).append("</ShapeType>\n")
                .append(tab).append("\t").append("<ShapeId>").append(field_1_shapeId).append("</ShapeId>\n")
                .append(tab).append("\t").append("<Flags>").append(decodeFlags(field_2_flags) + " (0x" + HexDump.toHex(field_2_flags) + ")").append("</Flags>\n");
        builder.append(tab).append("</").append(getClass().getSimpleName()).append(">\n");
        return builder.toString();
    }

    /**
     * Converts the shape flags into a more descriptive name.
     */
    private String decodeFlags( int flags )
    {
        StringBuffer result = new StringBuffer();
        result.append( ( flags & FLAG_GROUP ) != 0 ? "|GROUP" : "" );
        result.append( ( flags & FLAG_CHILD ) != 0 ? "|CHILD" : "" );
        result.append( ( flags & FLAG_PATRIARCH ) != 0 ? "|PATRIARCH" : "" );
        result.append( ( flags & FLAG_DELETED ) != 0 ? "|DELETED" : "" );
        result.append( ( flags & FLAG_OLESHAPE ) != 0 ? "|OLESHAPE" : "" );
        result.append( ( flags & FLAG_HAVEMASTER ) != 0 ? "|HAVEMASTER" : "" );
        result.append( ( flags & FLAG_FLIPHORIZ ) != 0 ? "|FLIPHORIZ" : "" );
        result.append( ( flags & FLAG_FLIPVERT ) != 0 ? "|FLIPVERT" : "" );
        result.append( ( flags & FLAG_CONNECTOR ) != 0 ? "|CONNECTOR" : "" );
        result.append( ( flags & FLAG_HAVEANCHOR ) != 0 ? "|HAVEANCHOR" : "" );
        result.append( ( flags & FLAG_BACKGROUND ) != 0 ? "|BACKGROUND" : "" );
        result.append( ( flags & FLAG_HASSHAPETYPE ) != 0 ? "|HASSHAPETYPE" : "" );

        //need to check, else blows up on some records - bug 34435
        if(result.length() > 0) {
            result.deleteCharAt(0);
        }
        return result.toString();
    }

    /**
     * @return  A number that identifies this shape
     */
    public int getShapeId()
    {
        return field_1_shapeId;
    }

    /**
     * Sets a number that identifies this shape.
     */
    public void setShapeId( int field_1_shapeId )
    {
        this.field_1_shapeId = field_1_shapeId;
    }

    /**
     * The flags that apply to this shape.
     *
     * @see #FLAG_GROUP
     * @see #FLAG_CHILD
     * @see #FLAG_PATRIARCH
     * @see #FLAG_DELETED
     * @see #FLAG_OLESHAPE
     * @see #FLAG_HAVEMASTER
     * @see #FLAG_FLIPHORIZ
     * @see #FLAG_FLIPVERT
     * @see #FLAG_CONNECTOR
     * @see #FLAG_HAVEANCHOR
     * @see #FLAG_BACKGROUND
     * @see #FLAG_HASSHAPETYPE
     */
    public int getFlags()
    {
        return field_2_flags;
    }

    /**
     * The flags that apply to this shape.
     *
     * @see #FLAG_GROUP
     * @see #FLAG_CHILD
     * @see #FLAG_PATRIARCH
     * @see #FLAG_DELETED
     * @see #FLAG_OLESHAPE
     * @see #FLAG_HAVEMASTER
     * @see #FLAG_FLIPHORIZ
     * @see #FLAG_FLIPVERT
     * @see #FLAG_CONNECTOR
     * @see #FLAG_HAVEANCHOR
     * @see #FLAG_BACKGROUND
     * @see #FLAG_HASSHAPETYPE
     */
    public void setFlags( int field_2_flags )
    {
        this.field_2_flags = field_2_flags;
    }

    /**
     * Returns shape type. Must be one of MSOSPT values (see [MS-ODRAW] for
     * details).
     * 
     * @return shape type
     */
    public short getShapeType()
    {
        return getInstance();
    }

    /**
     * Sets shape type. Must be one of MSOSPT values (see [MS-ODRAW] for
     * details).
     * 
     * @param value
     *            new shape type
     */
    public void setShapeType( short value )
    {
        setInstance( value );
    }
    
    //20101015, henrichen@zkoss.org: enhance to provide easy access API
    private static final BitField group = BitFieldFactory.getInstance(FLAG_GROUP);
    private static final BitField child = BitFieldFactory.getInstance(FLAG_CHILD);
    private static final BitField patriarch = BitFieldFactory.getInstance(FLAG_PATRIARCH);
    private static final BitField deleted = BitFieldFactory.getInstance(FLAG_DELETED);
    private static final BitField oleshape = BitFieldFactory.getInstance(FLAG_OLESHAPE);
    private static final BitField havemaster = BitFieldFactory.getInstance(FLAG_HAVEMASTER);
    private static final BitField fliphoriz = BitFieldFactory.getInstance(FLAG_FLIPHORIZ);
    private static final BitField flipvert = BitFieldFactory.getInstance(FLAG_FLIPVERT);
    private static final BitField connector = BitFieldFactory.getInstance(FLAG_CONNECTOR);
    private static final BitField haveanchor = BitFieldFactory.getInstance(FLAG_HAVEANCHOR);
    private static final BitField backgroud = BitFieldFactory.getInstance(FLAG_BACKGROUND);
    private static final BitField hasshapetype = BitFieldFactory.getInstance(FLAG_HASSHAPETYPE);

    public boolean isGroup() {
    	return group.isSet(field_2_flags);
    }
    public boolean isChild() {
    	return child.isSet(field_2_flags);
    }
    public boolean isPatriarch() {
    	return patriarch.isSet(field_2_flags);
    }
    public boolean isDeleted() {
    	return deleted.isSet(field_2_flags);
    }
    public boolean isOleShape() {
    	return oleshape.isSet(field_2_flags);
    }
    public boolean isHaveMaster() {
    	return havemaster.isSet(field_2_flags);
    }
    public boolean isFlipH() {
    	return fliphoriz.isSet(field_2_flags);
    }
    public boolean isFlipV() {
    	return flipvert.isSet(field_2_flags);
    }
    public boolean isConnector() {
    	return connector.isSet(field_2_flags);
    }
    public boolean isHaveAnchor() {
    	return haveanchor.isSet(field_2_flags);
    }
    public boolean isBackgroud() {
    	return backgroud.isSet(field_2_flags);
    }
    public boolean isHasSpt() {
    	return hasshapetype.isSet(field_2_flags);
    }
}
