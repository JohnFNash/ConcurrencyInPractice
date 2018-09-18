package com.johnfnash.learn.activeness.livelock;

/************************
 * �������� ����һ�������࣬����ֻ��һ���� �ɷ�������ò�ʱ����Ҫʹ�����ӣ���ʱֻ����һ�˳��У�Ҳ����˵ͬһʱ��ֻ��һ�����ܹ����͡�
 * �����ɷ�����ӻ���ǫ�ã������öԷ��ȳԣ���������һֱ����������ȥ��˭��û���ò͡�
 */
public class LiveLockTest {

	// ����һ�����ӣ�owner ��ʾ������ӵ�ӵ����
	static class Spoon {
		Diner owner; // ���ӵ�ӵ����

		public Spoon(Diner owner) {
			this.owner = owner;
		}

		// ��ȡӵ����
		public String getOwnerName() {
			return owner.getName();
		}
		
		//����ӵ����
        public void setOwner(Diner diner) {
            this.owner = diner;
        }
        
        //��ʾ�����ò�
        public void use() {
        	System.out.println(owner.getName() + " use this spoon and finish eat.");
        }
        
	}

	// ����һ�������
	static class Diner {
		private boolean isHungry;// �Ƿ����
		private String name;// ���嵱ǰ�ò��ߵ�����

		public Diner(boolean isHungry, String name) {
            this.isHungry = isHungry;
            this.name = name;
		}
		
		//��ȡ��ǰ�ò���
		public String getName() {
            return name;
        }
		
		// �������Ϊ��ĳ�˳Է�
		public void eatWith(Diner spouse, Spoon sharedSpoon) {
			try {
				synchronized (sharedSpoon) {
					while(isHungry) {
						//��ǰ�ò��ߺ�����ӵ���߲���ͬһ���ˣ�����еȴ�
						while(!sharedSpoon.getOwnerName().equals(name)) {
							sharedSpoon.wait();
						}
						
						//spouse��ʱ�Ƕ��ˣ������ӷָ�������֪ͨ�������ò�
						if(spouse.isHungry) {
							System.out.println("I am " + name + ", and my " + spouse.getName() + " is hungry, I should give it to him(her).\n");
                            sharedSpoon.setOwner(spouse);
                            sharedSpoon.notifyAll();
						} else {
							//�ò�
							sharedSpoon.use();
							sharedSpoon.setOwner(spouse);
							isHungry = false;
						}
						Thread.sleep(500);
					}
				}
			} catch (InterruptedException e) {
				 System.out.println(name + " is interrupted.");
			}
		}
	}

	public static void main(String[] args) {
		final Diner husband = new Diner(true, "husband");//����һ���ɷ��ò���
        final Diner wife = new Diner(true, "wife");//����һ�������ò���
        final Spoon sharedSpoon = new Spoon(wife);//����һ�����ӣ���ʼ״̬�������ӳ���
        
        //����һ�� �̣߳����ɷ�����ò�
        Thread h = new Thread() {
        	@Override
        	public void run() {
        		//��ʾ�������òͣ���������ж������Ƿ���ˣ�����ǣ��������ӷָ����ӣ���֪ͨ��
        		husband.eatWith(wife, sharedSpoon);
        	}
        };
        h.start();
        
        //����һ�� �̣߳������ӽ����ò�
        Thread w = new Thread() {
            @Override
            public void run() {
                //��ʾ�������òͣ���������ж��ɷ��Ƿ���ˣ�����ǣ��������ӷָ��ɷ򣬲�֪ͨ��
                wife.eatWith(husband, sharedSpoon);
            }
        };
        w.start();
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        h.interrupt();
        w.interrupt();
        
        try {
        	//join()�����������ô˷������߳�(calling thread)��ֱ���߳�t��ɣ����߳��ټ�����ͨ��������main()���߳��ڣ��ȴ������߳�����ٽ���main()���߳�
			h.join();
			w.join();
		} catch (InterruptedException e) {
			
		}
	}
	
}
