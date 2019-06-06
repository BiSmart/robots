package gui;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import log.Logger;

public class MainMenu extends JMenuBar
{
	private JMenu testMenu;
	private JMenu lookAndFeelMenu;

	public MainMenu(ActionListener exitEvent) {
		testMenu = createMenu("Тесты", "Тестовые команды");
		lookAndFeelMenu = createMenu("Режим отображения", "Управление режимом отображения приложения");
		
		testMenu.add(createItem("Сообщение в лог", (e) -> {Logger.debug("Новая строка");} ));
		
		lookAndFeelMenu.add(createItem("Системная схема", (e) -> {
			setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			this.invalidate();
		}));
		lookAndFeelMenu.add(createItem("Универсальная схема", (e) -> {
			setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			this.invalidate();
		}));
		
		JMenu exitMenu = createMenu("Выход", "Выход из приложения");
		exitMenu.add(createItem("Выйти", exitEvent));
		
		this.add(exitMenu);
		this.add(testMenu);
		
		this.add(lookAndFeelMenu);
	}

	public void createLoader(ActionListener onLogicLoad) {
		JMenu loadMenu = createMenu("Загрузить логику бота", "логика");
		loadMenu.add(createItem("Выбрать файл", onLogicLoad));

		this.add(loadMenu);
	}

	private JMenu createMenu(String title, String desc)  {
		JMenu menu = new JMenu(title);

		menu.setMnemonic(KeyEvent.VK_T);
		menu.getAccessibleContext().setAccessibleDescription(
        		desc);

		return menu;
	}
	
	private JMenuItem createItem(String title, ActionListener event) {
		JMenuItem item = new JMenuItem(title, KeyEvent.VK_S);
		item.addActionListener(event);

		return item;
	}
	
	private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
