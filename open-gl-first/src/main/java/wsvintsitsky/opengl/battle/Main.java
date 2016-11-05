package wsvintsitsky.opengl.battle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class Main implements Runnable, KeyListener {
	public Thread displayT = new Thread(this);
	private static boolean bQuit = false;
	private SceneDrawer sceneDrawer;
	
	public static void main(String[] args) {
		Main javaDia = new Main();
		javaDia.displayT.start();
	}

	@Override
	public void run() {
		int width = 1024;
		int height = 768;

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);

		final GLCanvas glcanvas = new GLCanvas(capabilities);
		
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
		sceneDrawer = new SceneDrawer(width, height);
		glcanvas.addGLEventListener(sceneDrawer);
		
		glcanvas.requestFocus();
		while (!bQuit) {
			glcanvas.display();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
			sceneDrawer.keyPressed(e);
		} else {
			displayT = null;
			bQuit = true;
			System.exit(0);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
			sceneDrawer.keyReleased(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() != KeyEvent.VK_ESCAPE) {
			sceneDrawer.keyTyped(e);
		}
	}
}