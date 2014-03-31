import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* Julio Ribeiro e Mayara Cristina */

public class DigestCalculator
{

	public static void main( String[] args )
	{
		/* Confere os argumentos da linha de comando */
		if ( args.length < 3 )
		{
			System.err.println ("\nVoce deve enviar pelo menos 3 parametros");
			System.err.println ("Formato correto: java DigestCalculator Tipo_Digest Caminho_Arq1... Caminho_ArqN Caminho_ArqListaDigest");
			System.exit( 1 );
		}
		
		/* Verifica os parametros */
		List<String> paramList = Arrays.asList( args );
		String tipo = paramList.get( 0 );
		List<String> inputFileNames = paramList.subList( 1, paramList.size()-1 );
		String digestListFileName = paramList.get( paramList.size()-1 );
		
		HashMap< String, String > digestMap = getDigestMap( tipo, new File( digestListFileName ) );
		for ( String inputFileName: inputFileNames )
		{
			String verificaDigest = calculaDigest( tipo, new File( inputFileName ) );
			System.out.print( new File( inputFileName ).getName() + " " + tipo + " " + verificaDigest + " " );
			inputFileName = new File( inputFileName ).getName();
			
			if ( digestMap.containsKey( inputFileName ) && digestMap.get( inputFileName ).equals( verificaDigest ) )
			{
				System.out.println( "(OK)" );
			}
			else if ( digestMap.containsValue( verificaDigest ) )
			{
				System.out.println( "(COLISION)" );
			}
			else if ( digestMap.containsKey( inputFileName ))
			{
				System.out.println( "(NOT OK)" );					
			}
			else
			{
				System.out.println( "(NOT FOUND)" );
				insereNovoDigest( digestListFileName, inputFileName, tipo, verificaDigest );
			}
		}
	}
	
		/* Calcula o digest */
		public static String calculaDigest( String tipo, File inputFile )
		{
			if( !inputFile.isFile( ) ) 
			{
				System.err.println( "Arquivo " + inputFile.getName() + " nao foi encontrado." );
				System.exit(1);
			}
			try
			{
				/* define o tipo do digest (MD5/SHA1) */
				MessageDigest messageDigest = MessageDigest.getInstance( tipo );
				messageDigest.reset();
				
				/* insere os bytes do arquivo de entrada */
				byte[] plainText = Files.readAllBytes( Paths.get( inputFile.toURI() ) );
				messageDigest.update( plainText );
				
				/* Calcula o digest */
				byte[] data = messageDigest.digest();
				
				/* Converte pra Hexa */				
				StringBuffer buf = new StringBuffer();
				for(int i = 0; i < data.length; i++) {
					String hex = Integer.toHexString(0x0100 + (data[i] & 0x00FF)).substring(1);
					buf.append((hex.length() < 2 ? "0" : "") + hex);
				}
				return buf.toString();
				
			}
			catch ( IOException e )
			{
				System.err.println( "Arquivo " + inputFile.getName() + " nao pode ser lido." );
			}
			catch (java.security.NoSuchAlgorithmException e )
			{
				System.err.println( "Tipo_Digest " + tipo + " nao eh reconhecido." );
			}
			System.exit(1);
			return null;
		}
	
	/* ( arquivo -> digest ) */
		
	private static HashMap< String, String > getDigestMap( String tipo, File inputFile )
	{
		try
		{
			BufferedReader reader = new BufferedReader( new FileReader( inputFile ) );
			HashMap< String, String > digestMap = new HashMap< String, String >();
			String inputLine;
			
			while ( ( inputLine = reader.readLine() ) != null )
			{
				String[] input = inputLine.split(" ");
				for ( int i = 1 ; i < input.length ; i+=2 )
				{
					if ( tipo == null || input[i].equals( tipo ) )
					{
						digestMap.put( input[0], input[i+1] );
					}
				}
			}
			
			reader.close();
			return digestMap;
		}
		catch ( IOException e )
		{
			System.err.println( "Arquivo " + inputFile.getName() + " nao pode ser lido." );
		}
		System.exit(1);
		return null;
	}
	
	private static void insereNovoDigest( String listFile, String inputFile, String tipo, String digest )
	{
		HashMap< String, String > digestMap = getDigestMap( null, new File( listFile ) );
		
		/* Se arquivo ja existente, escreve na mesma linha */
		
		if ( digestMap.containsKey( new File( inputFile ).getName() ) )
		{
			try
			{
				List< String > lines = new ArrayList< String >();
				
				BufferedReader reader = new BufferedReader( new FileReader( listFile ) );
				while ( reader.ready() )
				{
					String line = reader.readLine();
					if ( line.startsWith( new File( inputFile ).getName() ) )
					{
						line = line.concat( " " + tipo + " " + digest );
					}
					lines.add( line );
				}
				reader.close();
				
				FileWriter writer = new FileWriter( new File( listFile ), false );
				for( String line: lines )
				{
					writer.write( line + System.getProperty("line.separator") );
				}
				writer.close();
			}
			catch ( IOException e )
			{
				System.err.println( "Arquivo " + listFile + " nao pode ser escrito." );
				System.exit(1);
			}
		}
		/* Se for aquivo novo, acrescenta nova linha */
		else
		{
			try
			{
				FileWriter fWriter = new FileWriter( listFile, true );
				BufferedWriter writer = new BufferedWriter( fWriter );
				writer.append( new File( inputFile ).getName() + " " + tipo + " " + digest );
				writer.newLine();
				writer.close();
			}
			catch ( IOException e )
			{
				System.err.println( "Arquivo " + listFile + " nao pode ser escrito." );
				System.exit(1);
			}
		}
	}
}
