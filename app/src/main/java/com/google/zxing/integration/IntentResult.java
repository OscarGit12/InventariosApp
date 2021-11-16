package com.google.zxing.integration;

import android.content.Intent;

/**
 * <p>Encapsulates the result of a barcode scan invoked through {@link IntentIntegrator}.</p>
 *
 * @author Sean Owen
 */
public final class IntentResult {

    private final String contents;
    private final String formatName;
    private final byte[] rawBytes;
    private final Integer orientation;
    private final String errorCorrectionLevel;
    private final String barcodeImagePath;
    private final Intent originalIntent;

    IntentResult() {
        this(null, null, null, null, null, null, null);
    }

    IntentResult(Intent intent) {
        this(null, null, null, null, null, null, intent);
    }

    IntentResult(String contents,
                 String formatName,
                 byte[] rawBytes,
                 Integer orientation,
                 String errorCorrectionLevel,
                 String barcodeImagePath,
                 Intent originalIntent) {
        this.contents = contents;
        this.formatName = formatName;
        this.rawBytes = rawBytes;
        this.orientation = orientation;
        this.errorCorrectionLevel = errorCorrectionLevel;
        this.barcodeImagePath = barcodeImagePath;
        this.originalIntent = originalIntent;
    }

    /**
     * @return raw content of barcode
     */
    public String getContents() {
        return contents;
    }

    /**
     * @return name of format, like "QR_CODE", "UPC_A". See {@code BarcodeFormat} for more format names.
     */
    public String getFormatName() {
        return formatName;
    }

    /**
     * @return raw bytes of the barcode content, if applicable, or null otherwise
     */
    public byte[] getRawBytes() {
        return rawBytes;
    }

    /**
     * @return rotation of the image, in degrees, which resulted in a successful scan. May be null.
     */
    public Integer getOrientation() {
        return orientation;
    }

    /**
     * @return name of the error correction level used in the barcode, if applicable
     */
    public String getErrorCorrectionLevel() {
        return errorCorrectionLevel;
    }

    /**
     * @return path to a temporary file containing the barcode image, if applicable, or null otherwise
     */
    public String getBarcodeImagePath() {
        return barcodeImagePath;
    }

    /**
     * @return the original intent
     */
    public Intent getOriginalIntent() {
        return originalIntent;
    }

    @Override
    public String toString() {
        int rawBytesLength = rawBytes == null ? 0 : rawBytes.length;
        return "Format: " + formatName + '\n' +
                "Contents: " + contents + '\n' +
                "Raw bytes: (" + rawBytesLength + " bytes)\n" +
                "Orientation: " + orientation + '\n' +
                "EC level: " + errorCorrectionLevel + '\n' +
                "Barcode image: " + barcodeImagePath + '\n' +
                "Original intent: " + originalIntent + '\n';
    }
}
