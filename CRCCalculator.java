import java.util.*;

public class CRCCalculator {

    // Function to compute CRC for given data and divisor
    public static String computeCRC(String data, String divisor) {
        int dataLen = data.length();
        int divisorLen = divisor.length();
        
        
        String augmentedData = data + "0".repeat(divisorLen - 1);
        
        
        char[] dataArr = augmentedData.toCharArray();
        char[] divisorArr = divisor.toCharArray();
        
       
        for (int i = 0; i <= dataLen - 1; i++) {
            
            if (dataArr[i] == '1') {
                for (int j = 0; j < divisorLen; j++) {
                    // XOR the bits
                    dataArr[i + j] = (dataArr[i + j] == divisorArr[j]) ? '0' : '1';
                }
            }
        }
        
        // The remainder is the CRC, which is the last (divisorLen - 1) bits
        String crc = new String(dataArr, dataLen, divisorLen - 1);
        return crc;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Data and divisor
        System.out.println("Enter data: ");
        String data = sc.nextLine();
        
        System.out.println("Enter divisor: ");
        String divisor = sc.nextLine();        
        // Compute the CRC
        String crc = computeCRC(data, divisor);
        
        // Display the original data, divisor, and the computed CRC
        System.out.println("Data: " + data);
        System.out.println("Divisor: " + divisor);
        System.out.println("CRC: " + crc);
    }
} 
