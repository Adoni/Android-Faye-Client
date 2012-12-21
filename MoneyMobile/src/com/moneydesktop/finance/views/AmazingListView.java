package com.moneydesktop.finance.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.moneydesktop.finance.adapters.AmazingAdapter;
import com.moneydesktop.finance.adapters.AmazingAdapter.HasMorePagesListener;

/**
 * A ListView that maintains a header pinned at the top of the list. The
 * pinned header can be pushed up and dissolved as needed.
 * 
 * It also supports pagination by setting a custom view as the loading
 * indicator.
 */
public class AmazingListView extends ListView implements HasMorePagesListener {
	public static final String TAG = AmazingListView.class.getSimpleName();
	
	View mListFooter;
	boolean mFooterViewAttached = false;
    boolean mEmptyViewAttached = false;

	private View mEmptyFooter;
	
    private View mHeaderView;
    private boolean mHeaderViewVisible;

    private int mHeaderViewWidth;
    private int mHeaderViewHeight;

    private AmazingAdapter mAdapter;
    
    public void setPinnedHeaderView(View view) {
        mHeaderView = view;

        // Disable vertical fading when the pinned header is present
        // TODO change ListView to allow separate measures for top and bottom fading edge;
        // in this particular case we would like to disable the top, but not the bottom edge.
        if (mHeaderView != null) {
            setFadingEdgeLength(0);
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mHeaderView != null) {
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
            configureHeaderView(getFirstVisiblePosition());
        }
    }

    public void configureHeaderView(int position) {
        if (mHeaderView == null) {
            return;
        }

        int state = mAdapter.getPinnedHeaderState(position);
        switch (state) {
            case AmazingAdapter.PINNED_HEADER_GONE: {
                mHeaderViewVisible = false;
                break;
            }

            case AmazingAdapter.PINNED_HEADER_VISIBLE: {
            	mAdapter.configurePinnedHeader(mHeaderView, position, 255);
                if (mHeaderView.getTop() != 0) {
                    mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);
                }
                mHeaderViewVisible = true;
                break;
            }

            case AmazingAdapter.PINNED_HEADER_PUSHED_UP: {
                View firstView = getChildAt(0);
                if (firstView != null) {
	                int bottom = firstView.getBottom();
	                int headerHeight = mHeaderView.getHeight();
	                int y;
	                int alpha;
	                if (bottom < headerHeight) {
	                    y = (bottom - headerHeight);
	                    alpha = 255; // * (headerHeight + y) / headerHeight;
	                } else {
	                    y = 0;
	                    alpha = 255;
	                }
	                mAdapter.configurePinnedHeader(mHeaderView, position, alpha);
	                if (mHeaderView.getTop() != y) {
	                    mHeaderView.layout(0, y, mHeaderViewWidth, mHeaderViewHeight + y);
	                }
	                mHeaderViewVisible = true;
                }
                break;
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderViewVisible) {
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
    }
    
    
    public AmazingListView(Context context) {
        super(context);
    }

    public AmazingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AmazingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public void setLoadingView(View listFooter) {
		this.mListFooter = listFooter;
	}
    
    public View getLoadingView() {
		return mListFooter;
	}
    
    public void setEmptyView(View emptyFooter) {
        this.mEmptyFooter = emptyFooter;
    }
    
    public View getEmptyView() {
        return mEmptyFooter;
    }
    
    @Override
    public void setAdapter(ListAdapter adapter) {
    	if (!(adapter instanceof AmazingAdapter)) {
    		throw new IllegalArgumentException(AmazingListView.class.getSimpleName() + " must use adapter of type " + AmazingAdapter.class.getSimpleName());
    	}
    	
    	// previous adapter
    	if (this.mAdapter != null) {
    		this.mAdapter.setHasMorePagesListener(null);
    		this.setOnScrollListener(null);
    	}
    	
    	this.mAdapter = (AmazingAdapter) adapter;
    	((AmazingAdapter) adapter).setHasMorePagesListener(this);
		this.setOnScrollListener((AmazingAdapter) adapter);
    	
		View dummy = new View(getContext());
    	super.addFooterView(dummy);
    	super.setAdapter(adapter);
    	super.removeFooterView(dummy);
    }
    
    @Override
    public AmazingAdapter getAdapter() {
    	return mAdapter;
    }

	@Override
	public void noMorePages() {
	    
		if (mListFooter != null) {
            this.removeFooterView(mListFooter);
            mFooterViewAttached = false;
        }
		    
	    if (!mEmptyViewAttached && mAdapter.getCount() == 0 && mEmptyFooter != null) {
	        this.addFooterView(mEmptyFooter);
	        mEmptyViewAttached = true;
	    }
	}

	@Override
	public void mayHaveMorePages() {
	    
		if (!mFooterViewAttached && mListFooter != null) {
		    this.removeFooterView(mEmptyFooter);
			this.addFooterView(mListFooter);
			mFooterViewAttached = true;
			mEmptyViewAttached = false;
		}
	}
	
	public boolean isLoadingViewVisible() {
		return mFooterViewAttached;
	}
}
