package wsvintsitsky.opengl.first;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class JavaDia implements Runnable, KeyListener {
	private static Thread displayT = new Thread(new JavaDia());
	private static boolean bQuit = false;

	public static void main(String[] args) {
		displayT.start();
	}

	@Override
	public void run() {
		int width = 640;
		int height = 480;

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		final GLCanvas glcanvas = new GLCanvas(capabilities);
		
		glcanvas.addGLEventListener(new Ground(-1.0f, -1.0f, 0.0f,
													-1.0f, -1.0f, -5.0f,
													 1.0f, -1.0f, -5.0f,
													 1.0f, -1.0f, 0.0f));
		
		float x = 0.0f;
		float y = -0.4f;
		float z = -3.0f;
		float angle = 0.0f;
		float scale = 3.0f;
		Plane plane = new Plane(x, y, z, width, height, scale, 0.16f, 0.06f, 0.16f, glcanvas);
		
//		glcanvas.addGLEventListener(new Triangle(0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.25f, 0.25f, 0.0f));
		
//		glcanvas.addGLEventListener(new Rock(-1.0f, -1.0f, -2.0f, width, height, 1.0f));
//		glcanvas.addGLEventListener(new Rock(-0.0f, -0.375f, -0.5f, width, height, 1.0f));
		
		glcanvas.setSize(width, height);

		JFrame frame = new JFrame("Battlefield");
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);

		glcanvas.addKeyListener(this);
		frame.pack();
		frame.setLocationRelativeTo(null);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				bQuit = true;
				System.exit(0);
			}
		});
		
//		plane.setAngle(270);
		
		glcanvas.requestFocus();
		while (!bQuit) {
			glcanvas.display();
			plane.setAngle(angle);
			angle += 0.02f;
//			y += 0.0001f;
//			plane.setY(y);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			displayT = null;
			bQuit = true;
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}